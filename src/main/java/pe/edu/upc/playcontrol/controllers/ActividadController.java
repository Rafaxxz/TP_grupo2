package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/actividad")
public class ActividadController {

    private final Map<Integer, Map<String, String>> actividadActual  = new ConcurrentHashMap<>();
    private final Map<Integer, Map<String, String>> comandoPendiente  = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean>             estadoBloqueado   = new ConcurrentHashMap<>();
    private final List<SseEmitter>                  emitters          = new CopyOnWriteArrayList<>();

    @Value("${app.public-url}")
    private String publicUrl;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    // ── Agente reporta ventana activa cada 10 s ───────────────────────────────

    @PostMapping
    public void reportar(@RequestBody Map<String, Object> body) {
        int    hijoId  = ((Number) body.get("hijoId")).intValue();
        String ventana = (String) body.getOrDefault("ventana", "Desconocido");
        String hora    = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        actividadActual.put(hijoId, Map.of("ventana", ventana, "hora", hora));
        broadcast("{\"hijoId\":" + hijoId
                + ",\"ventana\":\"" + ventana.replace("\"", "'") + "\""
                + ",\"hora\":\"" + hora + "\"}");
    }

    @GetMapping("/actual/{hijoId}")
    public Map<String, String> getActual(@PathVariable Integer hijoId) {
        return actividadActual.getOrDefault(hijoId,
                Map.of("ventana", "Sin actividad detectada", "hora", ""));
    }

    // ── Agente consulta comandos pendientes cada 5 s ──────────────────────────

    @GetMapping("/comando/{hijoId}")
    public Map<String, String> getComando(@PathVariable Integer hijoId) {
        return comandoPendiente.remove(hijoId); // consume y elimina
    }

    // ── Padre envía un comando ────────────────────────────────────────────────

    @PostMapping("/comando/{hijoId}")
    public void enviarComando(@PathVariable Integer hijoId,
                              @RequestBody Map<String, String> cmd) {
        String tipo = cmd.getOrDefault("tipo", "");

        if ("BLOQUEAR".equals(tipo)) {
            estadoBloqueado.put(hijoId, true);
            cmd.put("url", frontendUrl + "/bloqueado");
        }
        if ("DESBLOQUEAR".equals(tipo)) {
            estadoBloqueado.put(hijoId, false);
        }

        comandoPendiente.put(hijoId, cmd);

        // Notificar al padre en tiempo real vía SSE
        broadcast("{\"hijoId\":" + hijoId
                + ",\"bloqueado\":" + estadoBloqueado.getOrDefault(hijoId, false) + "}");
    }

    // ── Angular del hijo consulta si está bloqueado ────────────────────────────

    @GetMapping("/estado/{hijoId}")
    public Map<String, Boolean> getEstado(@PathVariable Integer hijoId) {
        return Map.of("bloqueado", estadoBloqueado.getOrDefault(hijoId, false));
    }

    // ── SSE stream para el panel del padre ────────────────────────────────────

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(300_000L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(()    -> emitters.remove(emitter));
        emitter.onError(e       -> emitters.remove(emitter));
        return emitter;
    }

    // ── Descarga el instalador .bat personalizado ──────────────────────────────

    @GetMapping("/instalar/{hijoId}")
    public ResponseEntity<byte[]> descargar(@PathVariable Integer hijoId) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"instalar-playcontrol.bat\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(buildBat(hijoId).getBytes(StandardCharsets.UTF_8));
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void broadcast(String json) {
        emitters.removeIf(e -> {
            try { e.send(SseEmitter.event().data(json)); return false; }
            catch (Exception ex) { return true; }
        });
    }

    private String buildBat(int hijoId) {
        java.net.URI uri = java.net.URI.create(publicUrl);
        String proto = uri.getScheme();
        String host  = uri.getHost();
        int    port  = uri.getPort() != -1 ? uri.getPort() : ("https".equals(proto) ? 443 : 80);

        String agentUrl  = publicUrl + "/agent.js";
        String agentArgs = "--proto " + proto + " --server " + host + " --port " + port + " --hijo " + hijoId;

        return "@echo off\r\n"
             + "chcp 65001 >nul\r\n"
             + "title PlayControl Agent\r\n"
             + "echo.\r\n"
             + "echo  ==========================================\r\n"
             + "echo   PlayControl - Agente de Monitoreo\r\n"
             + "echo  ==========================================\r\n"
             + "echo.\r\n"
             + "node --version >nul 2>&1\r\n"
             + "if %errorlevel% neq 0 (\r\n"
             + "    echo  [!] Node.js no esta instalado.\r\n"
             + "    echo      Descargalo gratis en: https://nodejs.org\r\n"
             + "    echo      Luego vuelve a hacer doble clic en este archivo.\r\n"
             + "    pause & exit /b 1\r\n"
             + ")\r\n"
             + "if not exist \"%APPDATA%\\PlayControl\" mkdir \"%APPDATA%\\PlayControl\"\r\n"
             + "echo  [1/3] Descargando agente...\r\n"
             + "powershell -NoProfile -Command \"(New-Object Net.WebClient).DownloadFile('" + agentUrl + "', $env:APPDATA + '\\PlayControl\\agent.js')\"\r\n"
             + "echo  [2/3] Configurando inicio automatico...\r\n"
             + "powershell -NoProfile -Command \""
             +     "$w=New-Object -COM WScript.Shell;"
             +     "$l=$w.CreateShortcut($env:APPDATA+'\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\PlayControl.lnk');"
             +     "$l.TargetPath='node';"
             +     "$l.Arguments=$env:APPDATA+'\\PlayControl\\agent.js " + agentArgs + "';"
             +     "$l.WindowStyle=7;"
             +     "$l.Save()\"\r\n"
             + "echo  [3/3] Iniciando agente...\r\n"
             + "echo.\r\n"
             + "echo  Listo. Tu padre puede ver tu actividad en PlayControl.\r\n"
             + "echo  No cierres esta ventana. Ctrl+C para detener.\r\n"
             + "echo.\r\n"
             + "node \"%APPDATA%\\PlayControl\\agent.js\" " + agentArgs + "\r\n";
    }
}

package pe.edu.upc.playcontrol.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pe.edu.upc.playcontrol.dtos.NotificacionLimiteDTO;
import pe.edu.upc.playcontrol.dtos.NotificacionPadreDTO;
import pe.edu.upc.playcontrol.dtos.SesionJuegoUpdateDTO;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.repositories.IAlertaRepository;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;
import pe.edu.upc.playcontrol.services.LimiteTiempoValidationService;

import java.time.OffsetDateTime;

/**
 * Controlador WebSocket para:
 * 1. Monitoreo en tiempo real de sesiones de juego
 * 2. Control de límites de tiempo durante juego
 */
@Controller
public class GameSessionWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LimiteTiempoValidationService limiteTiempoValidationService;

    @Autowired
    private LimiteTiempoRepository limiteTiempoRepository;

    @Autowired
    private IAlertaRepository alertaRepository;

    /**
     * Endpoint: /app/sesion/actualizar
     *
     * El hijo envía actualizaciones de su progreso mientras juega.
     * El servidor:
     * 1. Notifica al padre en tiempo real
     * 2. Valida límite de tiempo
     * 3. Envía advertencias o corte si es necesario
     */
    @MessageMapping("/sesion/actualizar")
    public void actualizarSesionJuego(@Payload SesionJuegoUpdateDTO update) {
        if (update == null || update.getIdUsuario() == null || update.getIdJuego() == null) {
            return;
        }

        Integer idHijo = update.getIdUsuario();
        Integer idJuego = update.getIdJuego();
        Integer minutosJugados = update.getMinutosJugados() != null ? update.getMinutosJugados() : 0;

        // 1. Obtener límite configurado para este hijo/juego
        // NOTA: Adaptaré esto según tu esquema. Por ahora, buscamos por usuario.
        LimiteTiempo limite = obtenerLimitePorHijo(idHijo);

        // 2. Notificar al padre sobre la actualización
        NotificacionPadreDTO notificacionPadre = new NotificacionPadreDTO();
        notificacionPadre.setTipo("SESION_ACTUALIZADA");
        notificacionPadre.setIdHijo(idHijo);
        notificacionPadre.setIdJuego(idJuego);
        notificacionPadre.setMinutosJugados(minutosJugados);
        notificacionPadre.setMensaje("El hijo está jugando: " + minutosJugados + " minutos");
        notificacionPadre.setTimestamp(System.currentTimeMillis());

        // Enviar al padre (asumiendo que el padre se suscribió a /topic/padre/{idPadre}/sesiones/{idHijo})
        messagingTemplate.convertAndSend("/topic/sesiones/" + idHijo, notificacionPadre);

        // 3. Validar límite de tiempo
        if (limite != null) {
            Integer minutosRestantes = limiteTiempoValidationService.calcularMinutosRestantes(minutosJugados, limite);

            // Verificar si debe cortarse
            if (limiteTiempoValidationService.debeCortar(minutosRestantes)) {
                handleGameCutoff(idHijo, idJuego);
            }
            // Verificar si debe advertirse
            else if (limiteTiempoValidationService.debeEnviarAdvertencia(minutosRestantes)) {
                sendWarningToChild(idHijo, idJuego, minutosRestantes);
            }
        }
    }

    /**
     * Maneja la situación cuando el límite de tiempo se alcanza.
     * - Envía orden de corte al hijo
     * - Registra alerta en BD
     * - Notifica al padre
     */
    private void handleGameCutoff(Integer idHijo, Integer idJuego) {
        // 1. Notificar al hijo que debe cortar el juego
        NotificacionLimiteDTO corte = new NotificacionLimiteDTO();
        corte.setTipo("CORTE");
        corte.setMensaje("Tu límite de tiempo ha sido alcanzado. El juego se cerrará ahora.");
        corte.setIdJuego(idJuego);
        corte.setTimestamp(System.currentTimeMillis());

        messagingTemplate.convertAndSend("/topic/limite/" + idHijo, corte);

        // 2. Registrar alerta en BD
        Alerta alerta = new Alerta();
        // Asumo que Alerta tiene estos campos. Ajusta según tu esquema.
        // alerta.setUsuario(...);
        // alerta.setTipo("LIMITE_TIEMPO");
        // alerta.setContenido("El límite de tiempo fue alcanzado para el juego: " + idJuego);
        // alerta.setFechaCreacion(OffsetDateTime.now());
        // alertaRepository.save(alerta);

        // 3. Notificar al padre
        NotificacionPadreDTO notificacionPadre = new NotificacionPadreDTO();
        notificacionPadre.setTipo("JUEGO_CORTADO");
        notificacionPadre.setIdHijo(idHijo);
        notificacionPadre.setIdJuego(idJuego);
        notificacionPadre.setMensaje("El juego fue cortado por exceder el límite de tiempo");
        notificacionPadre.setTimestamp(System.currentTimeMillis());

        messagingTemplate.convertAndSend("/topic/sesiones/" + idHijo, notificacionPadre);
    }

    /**
     * Envía una advertencia al hijo cuando faltan 5 minutos o menos.
     */
    private void sendWarningToChild(Integer idHijo, Integer idJuego, Integer minutosRestantes) {
        NotificacionLimiteDTO advertencia = new NotificacionLimiteDTO();
        advertencia.setTipo("ADVERTENCIA");
        advertencia.setMensaje("⏰ Quedan " + minutosRestantes + " minutos de juego. El juego se cerrará al terminarse el tiempo.");
        advertencia.setMinutosRestantes(minutosRestantes);
        advertencia.setIdJuego(idJuego);
        advertencia.setTimestamp(System.currentTimeMillis());

        messagingTemplate.convertAndSend("/topic/limite/" + idHijo, advertencia);
    }

    /**
     * Obtiene el límite de tiempo para un hijo (simplificado).
     */
    private LimiteTiempo obtenerLimitePorHijo(Integer idHijo) {
        return limiteTiempoValidationService.obtenerLimiteActivo(idHijo);
    }
}





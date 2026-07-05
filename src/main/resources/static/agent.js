// PlayControl Desktop Agent — zero npm dependencies
// Uso: node agent.js --proto https --server tuservidor.onrender.com --port 443 --hijo 3
const http = require('http');
const https = require('https');
const { execSync } = require('child_process');

const argv = process.argv.slice(2);
const args = {};
for (let i = 0; i < argv.length; i += 2) args[argv[i].replace('--', '')] = argv[i + 1];

const PROTO = args.proto || 'http';
const HOST = args.server || 'localhost';
const PORT = parseInt(args.port || (PROTO === 'https' ? '443' : '8080'));
const HIJO = parseInt(args.hijo  || '0');
const transport = PROTO === 'https' ? https : http;

if (!HIJO) { console.error('[PlayControl] Falta --hijo <id>'); process.exit(1); }

const hora = () => new Date().toLocaleTimeString('es-PE');

// ── Detectar ventana activa ──────────────────────────────────────────────────

function ventanaActiva() {
  try {
    if (process.platform === 'win32') {
      return execSync(
        'powershell -NoProfile -Command "Get-Process | Where-Object {$_.MainWindowTitle -ne \'\'} | Sort-Object CPU -Descending | Select-Object -First 1 -ExpandProperty MainWindowTitle"',
        { timeout: 3000, encoding: 'utf8' }
      ).trim() || 'Escritorio';
    }
    if (process.platform === 'darwin') {
      return execSync(
        "osascript -e 'tell app \"System Events\" to get name of first process whose frontmost is true'",
        { timeout: 3000, encoding: 'utf8' }
      ).trim() || 'Escritorio';
    }
    return execSync('xdotool getactivewindow getwindowname 2>/dev/null || echo Escritorio',
      { timeout: 3000, encoding: 'utf8' }).trim() || 'Escritorio';
  } catch { return 'Desconocido'; }
}

// ── Reportar al servidor ─────────────────────────────────────────────────────

function reportar(ventana) {
  const body = JSON.stringify({ hijoId: HIJO, ventana });
  const req  = transport.request({
    hostname: HOST, port: PORT, path: '/api/actividad',
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Content-Length': Buffer.byteLength(body) }
  });
  req.on('error', () => {});
  req.write(body); req.end();
}

// ── Ejecutar comandos del padre ──────────────────────────────────────────────

function ejecutarBloqueo(url) {
  const juegos = ['javaw.exe', 'Minecraft.exe', 'MinecraftLauncher.exe',
                  'steam.exe', 'EpicGamesLauncher.exe', 'robloxplayerbeta.exe',
                  'FortniteLauncher.exe', 'LeagueClient.exe'];

  if (process.platform === 'win32') {
    juegos.forEach(j => { try { execSync(`taskkill /F /IM "${j}" 2>nul`); } catch {} });
    try { execSync(`start "" "${url}"`); } catch {}
  } else if (process.platform === 'darwin') {
    ['Minecraft', 'Steam', 'Epic Games Launcher', 'Roblox'].forEach(app => {
      try { execSync(`osascript -e 'quit app "${app}"' 2>/dev/null`); } catch {}
    });
    try { execSync(`open "${url}"`); } catch {}
  }
  console.log(`  [${hora()}] 🔴 BLOQUEO ejecutado → juegos cerrados, navegador abierto`);
}

function mostrarMensaje(msg) {
  if (!msg) return;
  const safe = msg.replace(/['"\\]/g, ' ');
  if (process.platform === 'win32') {
    try {
      execSync(
        `powershell -Command "Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.MessageBox]::Show('${safe}','PlayControl - Mensaje de tu padre',[System.Windows.Forms.MessageBoxButtons]::OK,[System.Windows.Forms.MessageBoxIcon]::Information)"`,
        { timeout: 30000 }
      );
    } catch {}
  } else if (process.platform === 'darwin') {
    try { execSync(`osascript -e 'display dialog "${safe}" with title "PlayControl"'`); } catch {}
  }
  console.log(`  [${hora()}] 💬 Mensaje mostrado: ${msg}`);
}

function verificarComandos() {
  const req = transport.request(
    { hostname: HOST, port: PORT, path: `/api/actividad/comando/${HIJO}`, method: 'GET' },
    (res) => {
      let data = '';
      res.on('data', d => data += d);
      res.on('end', () => {
        if (!data || data === 'null') return;
        try {
          const cmd = JSON.parse(data);
          if (!cmd?.tipo) return;
          if (cmd.tipo === 'BLOQUEAR')  ejecutarBloqueo(cmd.url || `${PROTO}://${HOST}/bloqueado`);
          if (cmd.tipo === 'MENSAJE')   mostrarMensaje(cmd.mensaje || '');
          if (cmd.tipo === 'DESBLOQUEAR') console.log(`  [${hora()}] 🟢 Desbloqueo recibido`);
        } catch {}
      });
    }
  );
  req.on('error', () => {});
  req.end();
}

// ── Loop principal ───────────────────────────────────────────────────────────

console.log(`\n  PlayControl Agent — hijo #${HIJO}  →  ${PROTO}://${HOST}:${PORT}`);
console.log(`  Reportando ventana cada 10s, comandos cada 5s. Ctrl+C para detener.\n`);

reportar(ventanaActiva());
verificarComandos();

setInterval(() => {
  const v = ventanaActiva();
  console.log(`  [${hora()}]  ${v}`);
  reportar(v);
}, 10000);

setInterval(verificarComandos, 5000);

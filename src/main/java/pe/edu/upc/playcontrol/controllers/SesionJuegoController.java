package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.dtos.SesionJuegoRequest;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.util.ArrayList;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesimplements.JuegoEventosServiceImpl;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sesiones")
@CrossOrigin
public class SesionJuegoController {

    private final SesionJuegoService service;
    private final SesionJuegoRepository sesionRepo;
    private final LimiteTiempoRepository limiteRepo;
    private final JuegoEventosServiceImpl juegoEventosSvc;

    public SesionJuegoController(SesionJuegoService service,
                                  SesionJuegoRepository sesionRepo,
                                  LimiteTiempoRepository limiteRepo,
                                  JuegoEventosServiceImpl juegoEventosSvc) {
        this.service = service;
        this.sesionRepo = sesionRepo;
        this.limiteRepo = limiteRepo;
        this.juegoEventosSvc = juegoEventosSvc;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HIJO')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody SesionJuegoRequest req) {
        if (req.getUsuarioId() == null) return buildErrorResponse(HttpStatus.BAD_REQUEST, "El usuario es obligatorio");
        if (req.getJuegoId() == null)   return buildErrorResponse(HttpStatus.BAD_REQUEST, "El juego es obligatorio");
        if (req.getInicio() == null)     return buildErrorResponse(HttpStatus.BAD_REQUEST, "La fecha de inicio es obligatoria");

        // US34: verificar límite diario antes de guardar
        LocalDate hoy = req.getInicio().toLocalDate();
        List<LimiteTiempo> limites = limiteRepo.findByUsuario_IdUsuario(req.getUsuarioId());
        for (LimiteTiempo limite : limites) {
            if ("diario".equalsIgnoreCase(limite.getTipo()) && Boolean.TRUE.equals(limite.getBloqueoActivo())) {
                Integer minutosHoy = sesionRepo.sumMinutosByUsuarioAndFecha(req.getUsuarioId(), hoy);
                if (minutosHoy != null && minutosHoy >= limite.getMinutosMaximos()) {
                    return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                            "Límite diario de " + limite.getMinutosMaximos() + " minutos alcanzado. No se puede iniciar una nueva sesión.");
                }
            }
        }

        SesionJuego sesion = new SesionJuego();
        Usuario u = new Usuario(); u.setIdUsuario(req.getUsuarioId());
        Juego j = new Juego(); j.setIdJuego(req.getJuegoId());
        sesion.setUsuario(u);
        sesion.setJuego(j);
        sesion.setInicio(req.getInicio());
        sesion.setFin(req.getFin());
        sesion.setFecha(hoy);
        if (req.getInicio() != null && req.getFin() != null) {
            long mins = java.time.Duration.between(req.getInicio(), req.getFin()).toMinutes();
            sesion.setDuracionMinutos((int) Math.max(0, mins));
        }

        try {
            SesionJuego saved = service.guardar(sesion);
            // US37 + US48: si la sesión ya tiene fin (sesión completa registrada de una vez)
            if (req.getFin() != null) {
                juegoEventosSvc.procesarFinSesion(saved);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear sesión: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesiones: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            SesionJuego sesion = service.buscarPorId(id);
            if (sesion != null) return ResponseEntity.ok(sesion);
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Sesión no encontrada con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesión: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<?> historialPorUsuario(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(service.historialPorUsuario(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener historial: " + e.getMessage());
        }
    }

    // HIJO puede ver sus propias sesiones; PADRE y ADMIN pueden ver las de cualquiera
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorUsuario(usuarioId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener sesiones del usuario: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody SesionJuegoRequest req) {
        try {
            SesionJuego sesion = service.buscarPorId(id);
            if (sesion == null) return buildErrorResponse(HttpStatus.NOT_FOUND, "Sesión no encontrada");
            boolean estabaAbierta = sesion.getFin() == null;
            if (req.getFin() != null) sesion.setFin(req.getFin());
            if (req.getInicio() != null) sesion.setInicio(req.getInicio());
            // Calcular duración en minutos si hay inicio y fin
            if (sesion.getInicio() != null && sesion.getFin() != null) {
                long mins = java.time.Duration.between(sesion.getInicio(), sesion.getFin()).toMinutes();
                sesion.setDuracionMinutos((int) Math.max(0, mins));
            }
            SesionJuego saved = service.guardar(sesion);
            // US37 + US48: procesar eventos solo si se acaba de cerrar la sesión
            List<Logro> desbloqueados = new ArrayList<>();
            if (estabaAbierta && req.getFin() != null) {
                desbloqueados = juegoEventosSvc.procesarFinSesion(saved);
            }
            Map<String, Object> resp = new HashMap<>();
            resp.put("sesion", saved);
            resp.put("logrosDesbloqueados", desbloqueados.stream()
                .map(l -> Map.of("nombre", l.getNombre(), "puntos", l.getPuntosOtorgados()))
                .collect(java.util.stream.Collectors.toList()));
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar sesión: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/juego/{juegoId}")
    public ResponseEntity<?> buscarPorJuego(@PathVariable Integer juegoId) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorJuego(juegoId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesiones del juego: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/usuario/{usuarioId}/juego/{juegoId}")
    public ResponseEntity<?> buscarPorUsuarioYJuego(@PathVariable Integer usuarioId, @PathVariable Integer juegoId) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorUsuarioYJuego(usuarioId, juegoId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesiones: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/fecha")
    public ResponseEntity<?> buscarPorFecha(@RequestParam LocalDate fecha) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorFecha(fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesiones por fecha: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
}

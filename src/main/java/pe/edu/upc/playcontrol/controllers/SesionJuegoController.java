package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sesiones")
public class SesionJuegoController {

    private final SesionJuegoService service;

    public SesionJuegoController(SesionJuegoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HIJO')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody SesionJuego sesionJuego) {
        if (sesionJuego.getUsuario() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El usuario de la sesión es obligatorio");
        }
        if (sesionJuego.getJuego() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El juego de la sesión es obligatorio");
        }
        if (sesionJuego.getInicio() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "La fecha de inicio de la sesión es obligatoria");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(sesionJuego));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            SesionJuego sesion = service.buscarPorId(id);
            if (sesion != null) {
                return ResponseEntity.ok(sesion);
            }
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

    // ADMIN y PADRE pueden ver el historial de sesiones de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorUsuario(usuarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron sesiones para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener sesiones del usuario: " + e.getMessage());
        }
    }

    // Solo ADMIN puede consultar sesiones por fecha
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/fecha")
    public ResponseEntity<?> buscarPorFecha(@RequestParam LocalDate fecha) {
        try {
            List<SesionJuegoDTO> result = service.buscarPorFecha(fecha);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron sesiones para la fecha: " + fecha);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener sesiones por fecha: " + e.getMessage());
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

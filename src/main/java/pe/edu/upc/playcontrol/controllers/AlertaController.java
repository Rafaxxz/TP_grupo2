package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    // El PADRE también crea alertas (p. ej. aviso de bloqueo hacia su hijo)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Alerta alerta) {
        if (alerta.getUsuario() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El usuario de la alerta es obligatorio");
        }
        if (alerta.getTipo() == null || alerta.getTipo().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El tipo de alerta es obligatorio");
        }
        if (alerta.getMensaje() == null || alerta.getMensaje().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El mensaje de la alerta es obligatorio");
        }
        if (alerta.getNivel() == null || alerta.getNivel().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El nivel de la alerta es obligatorio");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(alerta));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear alerta: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener alertas: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Alerta alerta = service.buscarPorId(id);
            if (alerta != null) {
                return ResponseEntity.ok(alerta);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Alerta no encontrada con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener alerta: " + e.getMessage());
        }
    }

    // HIJO puede ver sus propias alertas; PADRE y ADMIN pueden ver las de cualquiera
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<AlertaDTO> result = service.buscarPorUsuario(usuarioId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener alertas del usuario: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PatchMapping("/{id}/leer")
    public ResponseEntity<?> marcarLeida(@PathVariable Integer id) {
        try {
            Alerta alerta = service.buscarPorId(id);
            if (alerta == null) return buildErrorResponse(HttpStatus.NOT_FOUND, "Alerta no encontrada");
            alerta.setLeida(true);
            service.guardar(alerta);
            return ResponseEntity.ok(Map.of("message", "Alerta marcada como leída"));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al marcar alerta: " + e.getMessage());
        }
    }

    // Solo ADMIN puede ver todas las alertas no leídas del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/no-leidas")
    public ResponseEntity<?> obtenerNoLeidas() {
        try {
            List<AlertaDTO> result = service.obtenerNoLeidas();
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND, "No hay alertas no leídas");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener alertas no leídas: " + e.getMessage());
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

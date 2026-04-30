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
import java.util.UUID;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlertaDTO>> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(alertaService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/no-leidas")
    public ResponseEntity<List<AlertaDTO>> obtenerNoLeidas() {
        return ResponseEntity.ok(alertaService.obtenerNoLeidas());
    }
}

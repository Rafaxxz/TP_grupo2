package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/limites")
public class LimiteTiempoController {

    private final LimiteTiempoService service;

    public LimiteTiempoController(LimiteTiempoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody LimiteTiempo limiteTiempo) {
        if (limiteTiempo.getUsuario() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El usuario del límite es obligatorio");
        }
        if (limiteTiempo.getTipo() == null || limiteTiempo.getTipo().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El tipo de límite es obligatorio");
        }
        if (limiteTiempo.getMinutosMaximos() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Los minutos máximos son obligatorios");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(limiteTiempo));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear límite de tiempo: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener límites de tiempo: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            LimiteTiempo limite = service.buscarPorId(id);
            if (limite != null) {
                return ResponseEntity.ok(limite);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Límite de tiempo no encontrado con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener límite de tiempo: " + e.getMessage());
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

package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar datos porque las relaciones @ManyToOne (usuario, sesionJuego) requieren objetos completos, no solo IDs. Crear AlertaDTO y convertir en el service.
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Alerta alerta) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(alerta));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear alerta: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON o exponer datos innecesarios. Usar AlertaDTO.
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener alertas: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON o exponer datos innecesarios. Usar AlertaDTO.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
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

    // no funciona corregir: Falta endpoint para obtener alertas no leídas por usuario (US02, US08)
    // no funciona corregir: Falta endpoint para marcar alerta como leída (US06, US08)
    // no funciona corregir: Falta endpoint para obtener alertas por usuario (US02, US17, US18)
    // no funciona corregir: Falta endpoint para filtrar alertas por periodo/fecha (US08)
    // no funciona corregir: Falta endpoint para actualizar alerta
    // no funciona corregir: Falta endpoint para eliminar alerta
}
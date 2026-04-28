package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/limites")
public class LimiteTiempoController {

    private final LimiteTiempoService service;

    public LimiteTiempoController(LimiteTiempoService service) {
        this.service = service;
    }

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar porque la relación @ManyToOne (usuario) requiere objeto completo, no ID. Ya existe LimiteTiempoDTO, implementar conversión en service.
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody LimiteTiempo limiteTiempo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(limiteTiempo));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear límite de tiempo: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe LimiteTiempoDTO, usarlo.
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener límites de tiempo: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe LimiteTiempoDTO, usarlo.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
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

    // no funciona corregir: Falta endpoint PUT /{id} para actualizar límite (US04, US05, US19)
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar límite
    // no funciona corregir: Falta endpoint para obtener límites por usuario (US04, US05, US17, US19)
    // no funciona corregir: Falta endpoint para obtener límite activo de un usuario por tipo (diario/semanal) (US04, US05)
    // no funciona corregir: Falta endpoint para verificar si usuario excedió límite (US02, US18)
    // no funciona corregir: Falta endpoint para activar/desactivar bloqueo temporal (US07)
}
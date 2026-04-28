package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICitaEspecialistaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas-especialista")
public class CitaEspecialistaController {

    @Autowired
    private ICitaEspecialistaService citaEspecialistaService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(citaEspecialistaService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener citas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return citaEspecialistaService.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND, "Cita no encontrada con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener cita: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CitaEspecialistaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(citaEspecialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear cita: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody CitaEspecialistaDTO dto) {
        try {
            dto.setIdCita(id);
            return ResponseEntity.ok(citaEspecialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar cita: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            citaEspecialistaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar cita: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint para obtener citas por usuario (US29, US37)
    // no funciona corregir: Falta endpoint para obtener citas por especialista (US29, US38)
    // no funciona corregir: Falta endpoint para actualizar estado de cita (confirmada, cancelada, completada) (US29)
    // no funciona corregir: Falta endpoint para obtener citas por rango de fechas (US29)
}

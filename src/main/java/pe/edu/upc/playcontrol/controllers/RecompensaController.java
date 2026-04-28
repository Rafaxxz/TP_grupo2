package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private IRecompensaService recompensaService;

    //devuelve todas las recompensas
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(recompensaService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener recompensas: " + e.getMessage());
        }
    }

    // devuelve una recompensa por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(recompensaService.getById(id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Recompensa no encontrada con id: " + id);
        }
    }

    //crea una nueva recompensa
    @PostMapping
    public ResponseEntity<?> save(@RequestBody RecompensaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear recompensa: " + e.getMessage());
        }
    }

    //actualiza una recompensa existente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody RecompensaDTO dto) {
        try {
            return ResponseEntity.ok(recompensaService.update(id, dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar recompensa: " + e.getMessage());
        }
    }

    //elimina una recompensa por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            recompensaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar recompensa: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint para filtrar recompensas por tipo (avatar, badge, premio) (US10, US14)
    // no funciona corregir: Falta endpoint para obtener recompensas disponibles según puntos del usuario (US10, US14)
    // no funciona corregir: Falta endpoint para obtener recompensas ordenadas por costo de puntos (US10)
}

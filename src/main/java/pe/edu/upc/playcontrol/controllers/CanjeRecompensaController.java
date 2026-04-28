package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/canjes")
public class CanjeRecompensaController {

    @Autowired
    private ICanjeRecompensaService canjeRecompensaService;

    //retorna todos los canjes
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(canjeRecompensaService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener canjes: " + e.getMessage());
        }
    }

    //retorna un canje por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(canjeRecompensaService.getById(id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Canje no encontrado con id: " + id);
        }
    }

    //todos los canjes realizados por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getByUsuarioId(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(canjeRecompensaService.getByUsuarioId(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener canjes del usuario: " + e.getMessage());
        }
    }

    //crea un nuevo canje
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CanjeRecompensaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(canjeRecompensaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear canje: " + e.getMessage());
        }
    }

    //elimina un canje por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            canjeRecompensaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar canje: " + e.getMessage());
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

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.MensajeDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IMensajeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(mensajeService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener mensajes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return mensajeService.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND, "Mensaje no encontrado con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener mensaje: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MensajeDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear mensaje: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody MensajeDTO dto) {
        try {
            dto.setIdMensaje(id);
            return ResponseEntity.ok(mensajeService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar mensaje: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            mensajeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar mensaje: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint para obtener mensajes por usuario remitente (US22, US37)
    // no funciona corregir: Falta endpoint para obtener mensajes por usuario destinatario (US22, US37)
    // no funciona corregir: Falta endpoint para marcar mensaje como leído (US22)
    // no funciona corregir: Falta endpoint para obtener conversación entre dos usuarios (US37)
}

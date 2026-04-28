package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController

@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private ILogroService logroService;

    // muestra todos los logros
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(logroService.getAll());   // 200 OK
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener logros: " + e.getMessage());
        }
    }

    // muestra un logro por id
    // @PathVariable extrae el {id} de la URL y lo convierte a UUID automáticamente.
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            var result = logroService.getById(id);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Logro no encontrado con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener logro: " + e.getMessage());
        }
    }

    // para crear un nuevo recurso
    @PostMapping
    public ResponseEntity<?> save(@RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroService.save(dto)); // 201 Created
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear logro: " + e.getMessage());
        }
    }

    // para reemplazar un recurso existente completo
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.ok(logroService.update(id, dto)); // 200 OK
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar logro: " + e.getMessage());
        }
    }

    // para eliminar un recurso
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            logroService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar logro: " + e.getMessage());
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

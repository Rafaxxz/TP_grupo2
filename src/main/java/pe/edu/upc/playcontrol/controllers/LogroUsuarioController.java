package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/logros-usuario")
public class LogroUsuarioController {

    @Autowired
    private ILogroUsuarioService logroUsuarioService;

    //devuelve todos los logros
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(logroUsuarioService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener logros de usuarios: " + e.getMessage());
        }
    }

    //devuelve un logro por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            var result = logroUsuarioService.getById(id);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Logro de usuario no encontrado con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener logro de usuario: " + e.getMessage());
        }
    }

    // todos los logros desbloqueados por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getByUsuarioId(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(logroUsuarioService.getByUsuarioId(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener logros del usuario: " + e.getMessage());
        }
    }

    //crea un nuevo logro
    @PostMapping
    public ResponseEntity<?> save(@RequestBody LogroUsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroUsuarioService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear logro de usuario: " + e.getMessage());
        }
    }

    //elimina un logro por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            logroUsuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar logro de usuario: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint para obtener estadísticas de logros de un usuario (total, desbloqueados, pendientes) (US11, US15)
    // no funciona corregir: Falta endpoint para verificar logros desbloqueables según progreso del usuario (US09)
    // no funciona corregir: Falta endpoint para obtener logros por periodo (últimos logros desbloqueados) (US15)
}

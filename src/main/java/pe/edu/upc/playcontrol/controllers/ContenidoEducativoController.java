package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IContenidoEducativoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/contenidos-educativos")
public class ContenidoEducativoController {

    @Autowired
    private IContenidoEducativoService contenidoEducativoService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(contenidoEducativoService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener contenidos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return contenidoEducativoService.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND, "Contenido no encontrado con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener contenido: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ContenidoEducativoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contenidoEducativoService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear contenido: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ContenidoEducativoDTO dto) {
        try {
            dto.setIdContenido(id);
            return ResponseEntity.ok(contenidoEducativoService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar contenido: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            contenidoEducativoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar contenido: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint para filtrar contenidos por tipo (articulo, video, guia, podcast) (US23, US24, US39)
    // no funciona corregir: Falta endpoint para buscar contenidos por palabra clave o tema (US23, US26)
    // no funciona corregir: Falta endpoint para obtener recursos descargables (US30)
    // no funciona corregir: Falta endpoint para obtener contenidos ordenados por fecha de publicación (US25, US40)
}

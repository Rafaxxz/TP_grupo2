package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

    private final JuegoService service;

    public JuegoController(JuegoService service) {
        this.service = service;
    }

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar porque la relación @ManyToOne (categoriaJuego) requiere objeto completo, no ID. Ya existe JuegoDTO, implementar conversión en service.
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Juego juego) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(juego));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear juego: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe JuegoDTO, usarlo.
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener juegos: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe JuegoDTO, usarlo.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        try {
            Juego juego = service.buscarPorId(id);
            if (juego != null) {
                return ResponseEntity.ok(juego);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Juego no encontrado con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener juego: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint PUT /{id} para actualizar juego
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar juego
    // no funciona corregir: Falta endpoint para buscar juegos por categoría (para organización)
    // no funciona corregir: Falta endpoint para buscar juegos por usuario (juegos que juega un usuario)
}
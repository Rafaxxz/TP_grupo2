package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

    private final JuegoService service;

    public JuegoController(JuegoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Juego juego) {
        if (juego.getNombre() == null || juego.getNombre().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El nombre del juego es obligatorio");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(juego));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear juego: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener juegos: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
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

    // ADMIN, PADRE e HIJO pueden buscar juegos por plataforma
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/plataforma")
    public ResponseEntity<?> buscarPorPlataforma(@RequestParam String plataforma) {
        try {
            List<JuegoDTO> result = service.buscarPorPlataforma(plataforma);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron juegos para la plataforma: " + plataforma);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar juegos por plataforma: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden buscar juegos por categoría
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable Integer idCategoria) {
        try {
            List<JuegoDTO> result = service.buscarPorCategoria(idCategoria);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron juegos para la categoría con id: " + idCategoria);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar juegos por categoría: " + e.getMessage());
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

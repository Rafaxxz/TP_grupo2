package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categorias-juego")
public class CategoriaJuegoController {

    private final CategoriaJuegoService service;

    public CategoriaJuegoController(CategoriaJuegoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody CategoriaJuego categoriaJuego) {
        if (categoriaJuego.getNombre() == null || categoriaJuego.getNombre().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El nombre de la categoría es obligatorio");
        }
        try {
            CategoriaJuegoDTO dto = new CategoriaJuegoDTO();
            dto.setNombre(categoriaJuego.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la categoría");
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener categorías: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden buscar categorías por nombre
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<CategoriaJuegoDTO> result = service.buscarPorNombre(nombre);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron categorías con nombre: " + nombre);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar categorías: " + e.getMessage());
        }
    }

    // Solo ADMIN puede verificar si existe una categoría por nombre
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/existe")
    public ResponseEntity<?> existePorNombre(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(service.existePorNombre(nombre));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al verificar la categoría: " + e.getMessage());
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

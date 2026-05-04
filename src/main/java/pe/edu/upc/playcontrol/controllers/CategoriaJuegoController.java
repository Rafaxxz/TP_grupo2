package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoriaJuegoService categoriaJuegoService;
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

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaJuegoDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaJuegoService.buscarPorNombre(nombre));
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> existePorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaJuegoService.existePorNombre(nombre));
    }
}

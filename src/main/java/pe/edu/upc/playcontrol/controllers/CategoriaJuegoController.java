package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-juego")
public class CategoriaJuegoController {

    @Autowired
    private CategoriaJuegoService categoriaJuegoService;

    @GetMapping
    public ResponseEntity<List<CategoriaJuegoDTO>> getAll() {
        return ResponseEntity.ok(categoriaJuegoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaJuegoDTO> getById(@PathVariable Integer id) {
        return categoriaJuegoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaJuegoDTO> save(@RequestBody CategoriaJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaJuegoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaJuegoDTO> update(@PathVariable Integer id, @RequestBody CategoriaJuegoDTO dto) {
        dto.setIdCategoria(id);
        return ResponseEntity.ok(categoriaJuegoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaJuegoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
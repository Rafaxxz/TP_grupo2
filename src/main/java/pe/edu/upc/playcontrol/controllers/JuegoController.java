package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<JuegoDTO>> getAll() {
        return ResponseEntity.ok(juegoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoDTO> getById(@PathVariable UUID id) {
        return juegoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JuegoDTO> save(@RequestBody JuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoDTO> update(@PathVariable UUID id, @RequestBody JuegoDTO dto) {
        dto.setIdJuego(id);
        return ResponseEntity.ok(juegoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
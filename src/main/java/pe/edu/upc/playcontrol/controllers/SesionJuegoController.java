package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sesiones")
public class SesionJuegoController {

    @Autowired
    private SesionJuegoService sesionJuegoService;

    @GetMapping
    public ResponseEntity<List<SesionJuegoDTO>> getAll() {
        return ResponseEntity.ok(sesionJuegoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionJuegoDTO> getById(@PathVariable UUID id) {
        return sesionJuegoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SesionJuegoDTO> save(@RequestBody SesionJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionJuegoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionJuegoDTO> update(@PathVariable UUID id, @RequestBody SesionJuegoDTO dto) {
        dto.setIdSesion(id);
        return ResponseEntity.ok(sesionJuegoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sesionJuegoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
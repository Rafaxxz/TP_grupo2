package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/limites")
public class LimiteTiempoController {

    @Autowired
    private LimiteTiempoService limiteTiempoService;

    @GetMapping
    public ResponseEntity<List<LimiteTiempoDTO>> getAll() {
        return ResponseEntity.ok(limiteTiempoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LimiteTiempoDTO> getById(@PathVariable UUID id) {
        return limiteTiempoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LimiteTiempoDTO> save(@RequestBody LimiteTiempoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(limiteTiempoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LimiteTiempoDTO> update(@PathVariable UUID id, @RequestBody LimiteTiempoDTO dto) {
        dto.setIdLimite(id);
        return ResponseEntity.ok(limiteTiempoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        limiteTiempoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
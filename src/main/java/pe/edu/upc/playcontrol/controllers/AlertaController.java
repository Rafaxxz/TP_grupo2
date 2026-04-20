package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> getAll() {
        return ResponseEntity.ok(alertaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> getById(@PathVariable UUID id) {
        return alertaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlertaDTO> save(@RequestBody AlertaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaDTO> update(@PathVariable UUID id, @RequestBody AlertaDTO dto) {
        dto.setIdAlerta(id);
        return ResponseEntity.ok(alertaService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
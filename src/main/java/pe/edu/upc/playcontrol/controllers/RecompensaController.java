package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private IRecompensaService recompensaService;

    @GetMapping
    public ResponseEntity<List<RecompensaDTO>> getAll() {
        return ResponseEntity.ok(recompensaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompensaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(recompensaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RecompensaDTO> save(@RequestBody RecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecompensaDTO> update(@PathVariable UUID id, @RequestBody RecompensaDTO dto) {
        return ResponseEntity.ok(recompensaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        recompensaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

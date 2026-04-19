package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos")
public class RetoController {

    @Autowired
    private IRetoService retoService;
    @GetMapping
    public ResponseEntity<List<RetoDTO>> getAll() {
        return ResponseEntity.ok(retoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetoDTO> getById(@PathVariable UUID id) {
        return retoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RetoDTO> save(@RequestBody RetoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(retoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetoDTO> update(@PathVariable UUID id, @RequestBody RetoDTO dto) {
        dto.setIdReto(id);
        return ResponseEntity.ok(retoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        retoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para obtener retos activos (US12, US16, US35)
    // no funciona corregir: Falta endpoint para filtrar retos por tipo (semanal, familiar, comunitario) (US12, US16, US35)
    // no funciona corregir: Falta endpoint para filtrar retos por dificultad (US12)
    // no funciona corregir: Falta endpoint para obtener retos disponibles para un usuario (US12, US16)
}

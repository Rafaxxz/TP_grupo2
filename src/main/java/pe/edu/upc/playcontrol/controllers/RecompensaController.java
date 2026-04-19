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

    //devuelve todas las recompensas
    @GetMapping
    public ResponseEntity<List<RecompensaDTO>> getAll() {
        return ResponseEntity.ok(recompensaService.getAll());
    }

    // devuelve una recompensa por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(recompensaService.getById(id)); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada con id: " + id); // 404 Not Found
        }
    }

    //crea una nueva recompensa
    @PostMapping
    public ResponseEntity<RecompensaDTO> save(@RequestBody RecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.save(dto));
    }

    //actualiza una recompensa existente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody RecompensaDTO dto) {
        try {
            return ResponseEntity.ok(recompensaService.update(id, dto)); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada con id: " + id); // 404 Not Found
        }
    }

    //elimina una recompensa por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            recompensaService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada con id: " + id); // 404 Not Found
        }
    }
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos")
public class RetoController {

    @Autowired
    private IRetoService retoService;

    @GetMapping
    public ResponseEntity<List<RetoDTO>> list() {
        return ResponseEntity.ok(retoService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<RetoDTO> insert(@RequestBody RetoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retoService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<RetoDTO> found = retoService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reto no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RetoDTO dto) {
        Optional<RetoDTO> existing = retoService.listId(dto.getIdReto());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reto no encontrado");
        }
        return ResponseEntity.ok(retoService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<RetoDTO> existing = retoService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reto no encontrado");
        }
        retoService.delete(id);
        return ResponseEntity.ok("Reto eliminado correctamente");
    }


    // - Falta endpoint para obtener retos activos (US12, US16, US35)
    // - Falta endpoint para filtrar retos por tipo (semanal, familiar, comunitario) (US12, US16, US35)
    // - Falta endpoint para filtrar retos por dificultad (US12)
    // - Falta endpoint para obtener retos disponibles para un usuario (US12, US16)
}

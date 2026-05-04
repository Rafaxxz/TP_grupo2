package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla recompensa
@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private IRecompensaService recompensaService;

    @GetMapping
    public ResponseEntity<List<RecompensaDTO>> list() {
        return ResponseEntity.ok(recompensaService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<RecompensaDTO> insert(@RequestBody RecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<RecompensaDTO> found = recompensaService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RecompensaDTO dto) {
        Optional<RecompensaDTO> existing = recompensaService.listId(dto.getIdRecompensa());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada");
        }
        return ResponseEntity.ok(recompensaService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<RecompensaDTO> existing = recompensaService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recompensa no encontrada");
        }
        recompensaService.delete(id);
        return ResponseEntity.ok("Recompensa eliminada correctamente");
    }

    // Filtro simple: trae recompensas filtradas por tipo
    @GetMapping("/por-tipo")
    public ResponseEntity<List<RecompensaDTO>> listByTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(recompensaService.listByTipo(tipo));
    }

    // Query 1: Recompensas disponibles para un usuario según puntos que tiene
    @GetMapping("/disponibles-por-puntos")
    public ResponseEntity<List<RecompensaDTO>> findDisponiblesPorPuntos(@RequestParam Integer puntosDisponibles) {
        return ResponseEntity.ok(recompensaService.findDisponiblesPorPuntos(puntosDisponibles));
    }

    // Query 2: Estadísticas de recompensas por tipo (cantidad, costo mín/máx/promedio)
    @GetMapping("/estadisticas-por-tipo")
    public ResponseEntity<?> findEstadisticasPorTipo() {
        return ResponseEntity.ok(recompensaService.findEstadisticasPorTipo());
    }
}

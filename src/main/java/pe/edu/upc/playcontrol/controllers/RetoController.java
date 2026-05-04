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

// Aquí se exponen los endpoints REST para la tabla reto
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

    // Filtro simple: trae retos según su tipo (semanal, familiar, comunitario)
    @GetMapping("/por-tipo")
    public ResponseEntity<List<RetoDTO>> listByTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(retoService.listByTipo(tipo));
    }

    // Filtro simple: trae retos activos o inactivos
    @GetMapping("/por-activo")
    public ResponseEntity<List<RetoDTO>> listByActivo(@RequestParam Boolean activo) {
        return ResponseEntity.ok(retoService.listByActivo(activo));
    }

    // Query 1: Retos activos disponibles filtrados por tipo con ordenamiento por dificultad
    @GetMapping("/activos-por-tipo")
    public ResponseEntity<List<RetoDTO>> listActivosByTipoOrdenado(@RequestParam String tipo) {
        return ResponseEntity.ok(retoService.listActivosByTipoOrdenado(tipo));
    }

    // Query 2: Próximos retos a vencer
    @GetMapping("/proximos-a-vencer")
    public ResponseEntity<List<RetoDTO>> listProximosAVencer() {
        return ResponseEntity.ok(retoService.listProximosAVencer());
    }
}

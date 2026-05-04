package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla logro
@RestController
@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private ILogroService logroService;

    @GetMapping
    public ResponseEntity<List<LogroDTO>> list() {
        return ResponseEntity.ok(logroService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<LogroDTO> insert(@RequestBody LogroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<LogroDTO> found = logroService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logro no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody LogroDTO dto) {
        Optional<LogroDTO> existing = logroService.listId(dto.getIdLogro());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logro no encontrado");
        }
        return ResponseEntity.ok(logroService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<LogroDTO> existing = logroService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logro no encontrado");
        }
        logroService.delete(id);
        return ResponseEntity.ok("Logro eliminado correctamente");
    }

    // Filtro simple: trae logros según su criterio de desbloqueo
    @GetMapping("/por-criterio")
    public ResponseEntity<List<LogroDTO>> listByCriterio(@RequestParam String criterio) {
        return ResponseEntity.ok(logroService.listByCriterio(criterio));
    }

    // Query 1: Logros disponibles filtrados por criterio con ordenamiento por dificultad
    @GetMapping("/criterio-ordenado")
    public ResponseEntity<List<LogroDTO>> listByCriterioOrdenado(@RequestParam String criterio) {
        return ResponseEntity.ok(logroService.listByCriterioOrdenado(criterio));
    }

    // Query 2: Estadísticas de logros con conteo de desbloqueos
    @GetMapping("/estadisticas-desbloqueos")
    public ResponseEntity<?> findConEstadisticasDesbloqueos() {
        return ResponseEntity.ok(logroService.findConEstadisticasDesbloqueos());
    }
}

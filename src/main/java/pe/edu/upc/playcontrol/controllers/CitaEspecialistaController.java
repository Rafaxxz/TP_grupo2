package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICitaEspecialistaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla cita_especialista
@RestController
@RequestMapping("/api/citas-especialista")
public class CitaEspecialistaController {

    @Autowired
    private ICitaEspecialistaService citaEspecialistaService;

    @GetMapping
    public ResponseEntity<List<CitaEspecialistaDTO>> list() {
        return ResponseEntity.ok(citaEspecialistaService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<CitaEspecialistaDTO> insert(@RequestBody CitaEspecialistaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaEspecialistaService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<CitaEspecialistaDTO> found = citaEspecialistaService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody CitaEspecialistaDTO dto) {
        Optional<CitaEspecialistaDTO> existing = citaEspecialistaService.listId(dto.getIdCita());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
        }
        return ResponseEntity.ok(citaEspecialistaService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<CitaEspecialistaDTO> existing = citaEspecialistaService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
        }
        citaEspecialistaService.delete(id);
        return ResponseEntity.ok("Cita eliminada correctamente");
    }

    // Filtro simple: trae citas de un usuario filtradas por estado (pendiente, confirmada, cancelada, completada)
    @GetMapping("/por-estado")
    public ResponseEntity<List<CitaEspecialistaDTO>> listByUsuarioIdAndEstado(
            @RequestParam UUID usuarioId,
            @RequestParam String estado) {
        return ResponseEntity.ok(citaEspecialistaService.listByUsuarioIdAndEstado(usuarioId, estado));
    }

    // Query de decisión: historial completo de citas de un usuario
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<List<CitaEspecialistaDTO>> listByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(citaEspecialistaService.listByUsuarioId(usuarioId));
    }

    // consultar validez: los siguientes van más allá del alcance de Semana 04 (requieren datos de otras tablas o lógica de negocio compleja)
    // - Falta endpoint para obtener citas por especialista (US29, US38)
    // - Falta endpoint para obtener citas por rango de fechas (US29)
}

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

    // - Falta endpoint para obtener citas por usuario (US29, US37)
    // - Falta endpoint para obtener citas por especialista (US29, US38)
    // - Falta endpoint para actualizar estado de cita (confirmada, cancelada, completada) (US29)
    // - Falta endpoint para obtener citas por rango de fechas (US29)
}

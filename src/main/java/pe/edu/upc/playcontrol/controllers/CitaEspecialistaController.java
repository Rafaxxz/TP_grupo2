package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICitaEspecialistaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas-especialista")
public class CitaEspecialistaController {

    @Autowired
    private ICitaEspecialistaService citaEspecialistaService;

    @GetMapping
    public ResponseEntity<List<CitaEspecialistaDTO>> getAll() {
        return ResponseEntity.ok(citaEspecialistaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaEspecialistaDTO> getById(@PathVariable UUID id) {
        return citaEspecialistaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaEspecialistaDTO> save(@RequestBody CitaEspecialistaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaEspecialistaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaEspecialistaDTO> update(@PathVariable UUID id, @RequestBody CitaEspecialistaDTO dto) {
        dto.setIdCita(id);
        return ResponseEntity.ok(citaEspecialistaService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        citaEspecialistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para obtener citas por usuario (US29, US37)
    // no funciona corregir: Falta endpoint para obtener citas por especialista (US29, US38)
    // no funciona corregir: Falta endpoint para actualizar estado de cita (confirmada, cancelada, completada) (US29)
    // no funciona corregir: Falta endpoint para obtener citas por rango de fechas (US29)
}

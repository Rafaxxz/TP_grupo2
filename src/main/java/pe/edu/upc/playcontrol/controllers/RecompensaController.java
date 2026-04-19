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
    public ResponseEntity<RecompensaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(recompensaService.getById(id));
    }

    //crea una nueva recompensa
    @PostMapping
    public ResponseEntity<RecompensaDTO> save(@RequestBody RecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.save(dto));
    }

    //actualiza una recompensa existente
    @PutMapping("/{id}")
    public ResponseEntity<RecompensaDTO> update(@PathVariable UUID id, @RequestBody RecompensaDTO dto) {
        return ResponseEntity.ok(recompensaService.update(id, dto));
    }

    //elimina una recompensa por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        recompensaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para filtrar recompensas por tipo (avatar, badge, premio) (US10, US14)
    // no funciona corregir: Falta endpoint para obtener recompensas disponibles según puntos del usuario (US10, US14)
    // no funciona corregir: Falta endpoint para obtener recompensas ordenadas por costo de puntos (US10)
}

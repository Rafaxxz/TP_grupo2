package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.EspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IEspecialistaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaController {

    @Autowired
    private IEspecialistaService especialistaService;

    @GetMapping
    public ResponseEntity<List<EspecialistaDTO>> getAll() {
        return ResponseEntity.ok(especialistaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaDTO> getById(@PathVariable UUID id) {
        return especialistaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EspecialistaDTO> save(@RequestBody EspecialistaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialistaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialistaDTO> update(@PathVariable UUID id, @RequestBody EspecialistaDTO dto) {
        dto.setIdEspecialista(id);
        return ResponseEntity.ok(especialistaService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        especialistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para buscar especialistas por especialidad o tipo (US29, US37)
    // no funciona corregir: Falta endpoint para ver disponibilidad de especialista (US29, US37)
    // no funciona corregir: Falta endpoint para obtener especialistas disponibles para citas (US29)
}

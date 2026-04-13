package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IContenidoEducativoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contenidos-educativos")
public class ContenidoEducativoController {

    @Autowired
    private IContenidoEducativoService contenidoEducativoService;

    @GetMapping
    public ResponseEntity<List<ContenidoEducativoDTO>> getAll() {
        return ResponseEntity.ok(contenidoEducativoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenidoEducativoDTO> getById(@PathVariable UUID id) {
        return contenidoEducativoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContenidoEducativoDTO> save(@RequestBody ContenidoEducativoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contenidoEducativoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenidoEducativoDTO> update(@PathVariable UUID id, @RequestBody ContenidoEducativoDTO dto) {
        dto.setIdContenido(id);
        return ResponseEntity.ok(contenidoEducativoService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        contenidoEducativoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

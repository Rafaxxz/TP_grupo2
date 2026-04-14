package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoUsuarioService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos-usuario")
public class RetoUsuarioController {

    @Autowired
    private IRetoUsuarioService retoUsuarioService;

    @GetMapping
    public ResponseEntity<List<RetoUsuarioDTO>> getAll() {
        return ResponseEntity.ok(retoUsuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetoUsuarioDTO> getById(@PathVariable UUID id) {
        return retoUsuarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RetoUsuarioDTO> save(@RequestBody RetoUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retoUsuarioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetoUsuarioDTO> update(@PathVariable UUID id, @RequestBody RetoUsuarioDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(retoUsuarioService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        retoUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logros-usuario")
public class LogroUsuarioController {

    @Autowired
    private ILogroUsuarioService logroUsuarioService;

    @GetMapping
    public ResponseEntity<List<LogroUsuarioDTO>> getAll() {
        return ResponseEntity.ok(logroUsuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogroUsuarioDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(logroUsuarioService.getById(id));
    }

    // GET /api/logros-usuario/usuario/{usuarioId} → todos los logros desbloqueados por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LogroUsuarioDTO>> getByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(logroUsuarioService.getByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<LogroUsuarioDTO> save(@RequestBody LogroUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroUsuarioService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logroUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

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

    //devuelve todos los logros
    @GetMapping
    public ResponseEntity<List<LogroUsuarioDTO>> getAll() {
        return ResponseEntity.ok(logroUsuarioService.getAll());
    }

    //devuelve un logro-usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(logroUsuarioService.getById(id)); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LogroUsuario no encontrado con id: " + id); // 404 Not Found
        }
    }

    // todos los logros desbloqueados por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LogroUsuarioDTO>> getByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(logroUsuarioService.getByUsuarioId(usuarioId));
    }

    //crea un nuevo logro-usuario (desbloquea un logro)
    @PostMapping
    public ResponseEntity<LogroUsuarioDTO> save(@RequestBody LogroUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroUsuarioService.save(dto));
    }

    //elimina un logro-usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            logroUsuarioService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LogroUsuario no encontrado con id: " + id); // 404 Not Found
        }
    }
}

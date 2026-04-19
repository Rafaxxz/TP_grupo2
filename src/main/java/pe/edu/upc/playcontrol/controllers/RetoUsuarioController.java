package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos-usuario")
public class RetoUsuarioController {

    @Autowired
    private IRetoUsuarioService retoUsuarioService;

    @GetMapping
    public ResponseEntity<List<RetoUsuarioDTO>> list() {
        return ResponseEntity.ok(retoUsuarioService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<RetoUsuarioDTO> insert(@RequestBody RetoUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retoUsuarioService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<RetoUsuarioDTO> found = retoUsuarioService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RetoUsuario no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RetoUsuarioDTO dto) {
        Optional<RetoUsuarioDTO> existing = retoUsuarioService.listId(dto.getId());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RetoUsuario no encontrado");
        }
        return ResponseEntity.ok(retoUsuarioService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<RetoUsuarioDTO> existing = retoUsuarioService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RetoUsuario no encontrado");
        }
        retoUsuarioService.delete(id);
        return ResponseEntity.ok("RetoUsuario eliminado correctamente");
    }


    // - Falta endpoint para obtener retos de un usuario específico (US12, US16, US35)
    // - Falta endpoint para actualizar progreso de reto (US12, US16)
    // - Falta endpoint para obtener retos completados por usuario (US12, US15)
    // - Falta endpoint para obtener retos en progreso de un usuario (US12)
}

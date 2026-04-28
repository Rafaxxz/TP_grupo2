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

// Aquí se exponen los endpoints REST para la tabla reto_usuario
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

    // Filtro simple: trae todos los retos aceptados por un usuario
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<List<RetoUsuarioDTO>> listByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(retoUsuarioService.listByUsuarioId(usuarioId));
    }

    // Query de decisión: retos completados (o no) de un usuario
    @GetMapping("/por-completado/{usuarioId}")
    public ResponseEntity<List<RetoUsuarioDTO>> listByUsuarioIdAndCompletado(
            @PathVariable UUID usuarioId,
            @RequestParam Boolean completado) {
        return ResponseEntity.ok(retoUsuarioService.listByUsuarioIdAndCompletado(usuarioId, completado));
    }

    // consultar validez: los siguientes van más allá del alcance de Semana 04 (requieren lógica de negocio o datos de otras tablas)
    // - Falta endpoint para actualizar progreso de reto (US12, US16)
    // - Falta endpoint para obtener retos en progreso de un usuario (US12)
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla logro_usuario
@RestController
@RequestMapping("/api/logros-usuario")
public class LogroUsuarioController {

    @Autowired
    private ILogroUsuarioService logroUsuarioService;

    @GetMapping
    public ResponseEntity<List<LogroUsuarioDTO>> list() {
        return ResponseEntity.ok(logroUsuarioService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<LogroUsuarioDTO> insert(@RequestBody LogroUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroUsuarioService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<LogroUsuarioDTO> found = logroUsuarioService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LogroUsuario no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody LogroUsuarioDTO dto) {
        Optional<LogroUsuarioDTO> existing = logroUsuarioService.listId(dto.getId());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LogroUsuario no encontrado");
        }
        return ResponseEntity.ok(logroUsuarioService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<LogroUsuarioDTO> existing = logroUsuarioService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LogroUsuario no encontrado");
        }
        logroUsuarioService.delete(id);
        return ResponseEntity.ok("LogroUsuario eliminado correctamente");
    }

    // Filtro simple: trae todos los logros desbloqueados por un usuario
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<List<LogroUsuarioDTO>> listByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(logroUsuarioService.listByUsuarioId(usuarioId));
    }

    // Query de decisión: cuántos logros ha desbloqueado un usuario
    @GetMapping("/conteo/{usuarioId}")
    public ResponseEntity<Long> contarLogrosPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(logroUsuarioService.contarLogrosPorUsuario(usuarioId));
    }

    // Query 1: Dashboard de progreso - consolidado de logros
    @GetMapping("/dashboard/{usuarioId}")
    public ResponseEntity<?> dashboardProgresoLogros(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(logroUsuarioService.dashboardProgresoLogros(usuarioId));
    }

    // Query 2: Timeline de logros desbloqueados en un rango de fechas
    @GetMapping("/timeline/{usuarioId}")
    public ResponseEntity<?> findTimelineDesbloqueos(@PathVariable UUID usuarioId,
                                                      @RequestParam(required = false) String fechaInicio,
                                                      @RequestParam(required = false) String fechaFin) {
        java.time.OffsetDateTime inicio = fechaInicio != null ? java.time.OffsetDateTime.parse(fechaInicio) : null;
        java.time.OffsetDateTime fin = fechaFin != null ? java.time.OffsetDateTime.parse(fechaFin) : null;
        return ResponseEntity.ok(logroUsuarioService.findTimelineDesbloqueos(usuarioId, inicio, fin));
    }
}

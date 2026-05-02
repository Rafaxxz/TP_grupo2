package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla canje_recompensa
@RestController
@RequestMapping("/api/canjes")
public class CanjeRecompensaController {

    @Autowired
    private ICanjeRecompensaService canjeRecompensaService;

    @GetMapping
    public ResponseEntity<List<CanjeRecompensaDTO>> list() {
        return ResponseEntity.ok(canjeRecompensaService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<CanjeRecompensaDTO> insert(@RequestBody CanjeRecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(canjeRecompensaService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<CanjeRecompensaDTO> found = canjeRecompensaService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canje no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody CanjeRecompensaDTO dto) {
        Optional<CanjeRecompensaDTO> existing = canjeRecompensaService.listId(dto.getIdCanje());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canje no encontrado");
        }
        return ResponseEntity.ok(canjeRecompensaService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<CanjeRecompensaDTO> existing = canjeRecompensaService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canje no encontrado");
        }
        canjeRecompensaService.delete(id);
        return ResponseEntity.ok("Canje eliminado correctamente");
    }

    // Filtro simple: trae todos los canjes realizados por un usuario
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<List<CanjeRecompensaDTO>> listByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(canjeRecompensaService.listByUsuarioId(usuarioId));
    }

    // Query de decisión: total de puntos que ha gastado un usuario en canjes
    @GetMapping("/puntos-gastados/{usuarioId}")
    public ResponseEntity<Integer> totalPuntosGastados(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(canjeRecompensaService.totalPuntosGastadosPorUsuario(usuarioId));
    }

    // Query 1: Balance actual - puntos ganados vs gastados
    @GetMapping("/balance/{usuarioId}")
    public ResponseEntity<?> balanceActualPuntos(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(canjeRecompensaService.balanceActualPuntos(usuarioId));
    }

    // Query 2: Historial de canjes - recompensas reclamadas vs disponibles
    @GetMapping("/historial-vs-disponibles/{usuarioId}")
    public ResponseEntity<?> historialCanjesVsDisponibles(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(canjeRecompensaService.historialCanjesVsDisponibles(usuarioId));
    }
}

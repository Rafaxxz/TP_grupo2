package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/canjes")
public class CanjeRecompensaController {

    @Autowired
    private ICanjeRecompensaService canjeRecompensaService;

    // Solo ADMIN ve el listado completo de todos los canjes del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(canjeRecompensaService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de canjes: " + e.getMessage());
        }
    }

    // ADMIN e HIJO pueden realizar un canje de recompensa
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HIJO')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody CanjeRecompensaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(canjeRecompensaService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el canje: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de un canje
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<CanjeRecompensaDTO> found = canjeRecompensaService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el canje con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el canje: " + e.getMessage());
        }
    }

    // Solo ADMIN puede modificar un canje existente
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody CanjeRecompensaDTO dto) {
        try {
            Optional<CanjeRecompensaDTO> existing = canjeRecompensaService.listId(dto.getIdCanje());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el canje con id: " + dto.getIdCanje());
            }
            return ResponseEntity.ok(canjeRecompensaService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el canje: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar un canje
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<CanjeRecompensaDTO> existing = canjeRecompensaService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el canje con id: " + id);
            }
            canjeRecompensaService.delete(id);
            return ResponseEntity.ok("Canje eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el canje: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver los canjes de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<?> listByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            List<CanjeRecompensaDTO> result = canjeRecompensaService.listByUsuarioId(usuarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron canjes para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener los canjes del usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el total de puntos gastados por un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/puntos-gastados/{usuarioId}")
    public ResponseEntity<?> totalPuntosGastados(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(canjeRecompensaService.totalPuntosGastadosPorUsuario(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al calcular los puntos gastados: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el balance de puntos de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/balance/{usuarioId}")
    public ResponseEntity<?> balanceActualPuntos(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(canjeRecompensaService.balanceActualPuntos(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el balance de puntos: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el historial de canjes vs recompensas disponibles
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/historial-vs-disponibles/{usuarioId}")
    public ResponseEntity<?> historialCanjesVsDisponibles(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(canjeRecompensaService.historialCanjesVsDisponibles(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el historial de canjes: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
}

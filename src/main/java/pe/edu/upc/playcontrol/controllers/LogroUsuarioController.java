package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/logros-usuario")
public class LogroUsuarioController {

    @Autowired
    private ILogroUsuarioService logroUsuarioService;

    // Solo ADMIN ve el listado completo de logros-usuario del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(logroUsuarioService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de logros de usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden registrar el desbloqueo de un logro
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody LogroUsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroUsuarioService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el logro de usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden consultar el detalle de un logro-usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<LogroUsuarioDTO> found = logroUsuarioService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el logro de usuario con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el logro de usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden actualizar el estado de un logro-usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody LogroUsuarioDTO dto) {
        try {
            Optional<LogroUsuarioDTO> existing = logroUsuarioService.listId(dto.getId());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el logro de usuario con id: " + dto.getId());
            }
            return ResponseEntity.ok(logroUsuarioService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el logro de usuario: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar registros de logros-usuario
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<LogroUsuarioDTO> existing = logroUsuarioService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el logro de usuario con id: " + id);
            }
            logroUsuarioService.delete(id);
            return ResponseEntity.ok("Logro de usuario eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el logro de usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver todos los logros desbloqueados de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<?> listByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            List<LogroUsuarioDTO> result = logroUsuarioService.listByUsuarioId(usuarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron logros para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener los logros del usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el conteo de logros desbloqueados de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/conteo/{usuarioId}")
    public ResponseEntity<?> contarLogrosPorUsuario(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(logroUsuarioService.contarLogrosPorUsuario(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al contar los logros del usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el dashboard de progreso de logros de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/dashboard/{usuarioId}")
    public ResponseEntity<?> dashboardProgresoLogros(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(logroUsuarioService.dashboardProgresoLogros(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el dashboard de logros: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el timeline de logros desbloqueados en un rango de fechas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/timeline/{usuarioId}")
    public ResponseEntity<?> findTimelineDesbloqueos(
            @PathVariable Integer usuarioId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        try {
            OffsetDateTime inicio = fechaInicio != null ? OffsetDateTime.parse(fechaInicio) : null;
            OffsetDateTime fin = fechaFin != null ? OffsetDateTime.parse(fechaFin) : null;
            return ResponseEntity.ok(logroUsuarioService.findTimelineDesbloqueos(usuarioId, inicio, fin));
        } catch (DateTimeParseException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Formato de fecha inválido. Use el formato ISO-8601, ej: 2024-01-01T00:00:00-05:00");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el timeline de logros: " + e.getMessage());
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

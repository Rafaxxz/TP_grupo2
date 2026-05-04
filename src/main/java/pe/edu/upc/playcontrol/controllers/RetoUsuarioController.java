package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoUsuarioService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/retos-usuario")
public class RetoUsuarioController {

    @Autowired
    private IRetoUsuarioService retoUsuarioService;

    // Solo ADMIN ve el listado completo de todos los retos-usuario del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(retoUsuarioService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de retos de usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden aceptar/inscribirse a un reto
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody RetoUsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(retoUsuarioService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el reto de usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden consultar el detalle de un reto-usuario específico
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<RetoUsuarioDTO> found = retoUsuarioService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el reto de usuario con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el reto de usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden actualizar el estado de un reto (ej: marcar como completado)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RetoUsuarioDTO dto) {
        try {
            Optional<RetoUsuarioDTO> existing = retoUsuarioService.listId(dto.getId());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el reto de usuario con id: " + dto.getId());
            }
            return ResponseEntity.ok(retoUsuarioService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el reto de usuario: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar registros de retos-usuario
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<RetoUsuarioDTO> existing = retoUsuarioService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el reto de usuario con id: " + id);
            }
            retoUsuarioService.delete(id);
            return ResponseEntity.ok("Reto de usuario eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el reto de usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver todos los retos aceptados por un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/por-usuario/{usuarioId}")
    public ResponseEntity<?> listByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            List<RetoUsuarioDTO> result = retoUsuarioService.listByUsuarioId(usuarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener los retos del usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden filtrar retos completados o en progreso de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/por-completado/{usuarioId}")
    public ResponseEntity<?> listByUsuarioIdAndCompletado(
            @PathVariable Integer usuarioId,
            @RequestParam Boolean completado) {
        try {
            List<RetoUsuarioDTO> result = retoUsuarioService.listByUsuarioIdAndCompletado(usuarioId, completado);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos con estado completado=" + completado
                                + " para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar los retos del usuario: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el dashboard de progreso de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/dashboard/{usuarioId}")
    public ResponseEntity<?> dashboardProgresoUsuario(
            @PathVariable Integer usuarioId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        try {
            OffsetDateTime inicio = fechaInicio != null ? OffsetDateTime.parse(fechaInicio) : null;
            OffsetDateTime fin = fechaFin != null ? OffsetDateTime.parse(fechaFin) : null;
            return ResponseEntity.ok(retoUsuarioService.dashboardProgresoUsuario(usuarioId, inicio, fin));
        } catch (DateTimeParseException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Formato de fecha inválido. Use el formato ISO-8601, ej: 2024-01-01T00:00:00-05:00");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el dashboard de progreso: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver el historial de retos completados en un rango de fechas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/completados-por-fecha/{usuarioId}")
    public ResponseEntity<?> listCompletadosByFechaBetween(
            @PathVariable Integer usuarioId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        try {
            OffsetDateTime inicio = fechaInicio != null ? OffsetDateTime.parse(fechaInicio) : null;
            OffsetDateTime fin = fechaFin != null ? OffsetDateTime.parse(fechaFin) : null;
            List<RetoUsuarioDTO> result = retoUsuarioService.listCompletadosByFechaBetween(usuarioId, inicio, fin);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos completados en el rango de fechas para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Formato de fecha inválido. Use el formato ISO-8601, ej: 2024-01-01T00:00:00-05:00");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener retos completados por fecha: " + e.getMessage());
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

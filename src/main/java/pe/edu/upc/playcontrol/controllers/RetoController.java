package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RetoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/retos")
public class RetoController {

    @Autowired
    private IRetoService retoService;

    // ADMIN, PADRE e HIJO pueden ver el catálogo de retos
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(retoService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de retos: " + e.getMessage());
        }
    }

    // Solo ADMIN puede crear nuevos retos
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody RetoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(retoService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el reto: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de un reto
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<RetoDTO> found = retoService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el reto con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el reto: " + e.getMessage());
        }
    }

    // Solo ADMIN puede modificar un reto
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RetoDTO dto) {
        try {
            Optional<RetoDTO> existing = retoService.listId(dto.getIdReto());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el reto con id: " + dto.getIdReto());
            }
            return ResponseEntity.ok(retoService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el reto: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar un reto
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<RetoDTO> existing = retoService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el reto con id: " + id);
            }
            retoService.delete(id);
            return ResponseEntity.ok("Reto eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el reto: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden filtrar retos por tipo
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-tipo")
    public ResponseEntity<?> listByTipo(@RequestParam String tipo) {
        try {
            List<RetoDTO> result = retoService.listByTipo(tipo);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos con tipo: " + tipo);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar retos por tipo: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden filtrar retos por estado activo/inactivo
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-activo")
    public ResponseEntity<?> listByActivo(@RequestParam Boolean activo) {
        try {
            List<RetoDTO> result = retoService.listByActivo(activo);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos con estado activo=" + activo);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar retos por estado: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver retos activos por tipo ordenados por dificultad
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/activos-por-tipo")
    public ResponseEntity<?> listActivosByTipoOrdenado(@RequestParam String tipo) {
        try {
            List<RetoDTO> result = retoService.listActivosByTipoOrdenado(tipo);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos activos con tipo: " + tipo);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener retos activos por tipo: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver los retos próximos a vencer
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/proximos-a-vencer")
    public ResponseEntity<?> listProximosAVencer() {
        try {
            List<RetoDTO> result = retoService.listProximosAVencer();
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron retos próximos a vencer");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener retos próximos a vencer: " + e.getMessage());
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

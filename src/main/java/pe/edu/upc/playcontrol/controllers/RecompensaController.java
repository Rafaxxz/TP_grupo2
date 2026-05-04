package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private IRecompensaService recompensaService;

    // ADMIN, PADRE e HIJO pueden ver el catálogo de recompensas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(recompensaService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de recompensas: " + e.getMessage());
        }
    }

    // Solo ADMIN puede crear nuevas recompensas
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody RecompensaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar la recompensa: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de una recompensa
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<RecompensaDTO> found = recompensaService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró la recompensa con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar la recompensa: " + e.getMessage());
        }
    }

    // Solo ADMIN puede modificar una recompensa
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody RecompensaDTO dto) {
        try {
            Optional<RecompensaDTO> existing = recompensaService.listId(dto.getIdRecompensa());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró la recompensa con id: " + dto.getIdRecompensa());
            }
            return ResponseEntity.ok(recompensaService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar la recompensa: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar una recompensa
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<RecompensaDTO> existing = recompensaService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró la recompensa con id: " + id);
            }
            recompensaService.delete(id);
            return ResponseEntity.ok("Recompensa eliminada correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar la recompensa: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden filtrar recompensas por tipo
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-tipo")
    public ResponseEntity<?> listByTipo(@RequestParam String tipo) {
        try {
            List<RecompensaDTO> result = recompensaService.listByTipo(tipo);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron recompensas con tipo: " + tipo);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar recompensas por tipo: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver recompensas alcanzables según puntos disponibles
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/disponibles-por-puntos")
    public ResponseEntity<?> findDisponiblesPorPuntos(@RequestParam Integer puntosDisponibles) {
        try {
            List<RecompensaDTO> result = recompensaService.findDisponiblesPorPuntos(puntosDisponibles);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron recompensas disponibles para " + puntosDisponibles + " puntos");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener recompensas disponibles: " + e.getMessage());
        }
    }

    // Solo ADMIN puede ver las estadísticas globales de recompensas por tipo
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/estadisticas-por-tipo")
    public ResponseEntity<?> findEstadisticasPorTipo() {
        try {
            return ResponseEntity.ok(recompensaService.findEstadisticasPorTipo());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener estadísticas de recompensas: " + e.getMessage());
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

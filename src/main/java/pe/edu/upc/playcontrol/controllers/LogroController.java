package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private ILogroService logroService;

    // ADMIN, PADRE e HIJO pueden ver el catálogo de logros
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(logroService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de logros: " + e.getMessage());
        }
    }

    // Solo ADMIN puede crear nuevos logros
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el logro: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de un logro
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<LogroDTO> found = logroService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el logro con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el logro: " + e.getMessage());
        }
    }

    // Solo ADMIN puede modificar un logro
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody LogroDTO dto) {
        try {
            Optional<LogroDTO> existing = logroService.listId(dto.getIdLogro());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el logro con id: " + dto.getIdLogro());
            }
            return ResponseEntity.ok(logroService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el logro: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar un logro
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<LogroDTO> existing = logroService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el logro con id: " + id);
            }
            logroService.delete(id);
            return ResponseEntity.ok("Logro eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el logro: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden filtrar logros por criterio de desbloqueo
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-criterio")
    public ResponseEntity<?> listByCriterio(@RequestParam String criterio) {
        try {
            List<LogroDTO> result = logroService.listByCriterio(criterio);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron logros con criterio: " + criterio);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar logros por criterio: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver logros ordenados por dificultad según criterio
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/criterio-ordenado")
    public ResponseEntity<?> listByCriterioOrdenado(@RequestParam String criterio) {
        try {
            List<LogroDTO> result = logroService.listByCriterioOrdenado(criterio);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron logros con criterio: " + criterio);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener logros ordenados: " + e.getMessage());
        }
    }

    // Solo ADMIN puede ver las estadísticas globales de desbloqueos
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/estadisticas-desbloqueos")
    public ResponseEntity<?> findConEstadisticasDesbloqueos() {
        try {
            return ResponseEntity.ok(logroService.findConEstadisticasDesbloqueos());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener estadísticas de desbloqueos: " + e.getMessage());
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

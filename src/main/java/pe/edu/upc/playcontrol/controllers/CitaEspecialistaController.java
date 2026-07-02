package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICitaEspecialistaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas-especialista")
public class CitaEspecialistaController {

    @Autowired
    private ICitaEspecialistaService citaEspecialistaService;

    // Solo ADMIN ve el listado completo de todas las citas del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(citaEspecialistaService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener las citas: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden consultar una cita específica
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return citaEspecialistaService.getById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND,
                            "No se encontró la cita con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar la cita: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden agendar una cita con un especialista
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CitaEspecialistaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(citaEspecialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar la cita: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden modificar una cita existente
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CitaEspecialistaDTO dto) {
        try {
            dto.setIdCita(id);
            return ResponseEntity.ok(citaEspecialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar la cita: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar una cita
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            citaEspecialistaService.delete(id);
            return ResponseEntity.ok("Cita eliminada correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar la cita: " + e.getMessage());
        }
    }

    // ADMIN y PADRE pueden ver las citas agendadas de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            List<CitaEspecialistaDTO> result = citaEspecialistaService.getByUsuarioId(usuarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron citas para el usuario con id: " + usuarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener las citas del usuario: " + e.getMessage());
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

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.EspecialistaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IEspecialistaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaController {

    @Autowired
    private IEspecialistaService especialistaService;

    // ADMIN, PADRE e HIJO pueden ver el catálogo de especialistas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(especialistaService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener los especialistas: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de un especialista
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return especialistaService.getById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND,
                            "No se encontró el especialista con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el especialista: " + e.getMessage());
        }
    }

    // Solo ADMIN puede registrar un nuevo especialista
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody EspecialistaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(especialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el especialista: " + e.getMessage());
        }
    }

    // Solo ADMIN puede modificar los datos de un especialista
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody EspecialistaDTO dto) {
        try {
            dto.setIdEspecialista(id);
            return ResponseEntity.ok(especialistaService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el especialista: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar un especialista
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            especialistaService.delete(id);
            return ResponseEntity.ok("Especialista eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el especialista: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver solo los especialistas verificados
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/verificados")
    public ResponseEntity<?> especialistasVerificados() {
        try {
            List<EspecialistaDTO> lista = especialistaService.findByVerificadoTrue();
            if (lista.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron especialistas verificados");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener especialistas verificados: " + e.getMessage());
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

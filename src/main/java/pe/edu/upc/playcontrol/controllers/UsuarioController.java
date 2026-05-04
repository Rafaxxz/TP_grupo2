package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // Solo ADMIN puede ver el listado completo de usuarios del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(usuarioService.getAll());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el perfil de un usuario
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return usuarioService.getById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND,
                            "No se encontró el usuario con id: " + id));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el usuario: " + e.getMessage());
        }
    }

    // Endpoint abierto: registro de nuevos usuarios (sin autenticación)
    @PostMapping
    public ResponseEntity<?> save(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar el usuario: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden actualizar su propio perfil
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        try {
            dto.setIdUsuario(id);
            return ResponseEntity.ok(usuarioService.save(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar un usuario
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Solo ADMIN puede ver los usuarios registrados en los últimos 30 días
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/userLastDays")
    public ResponseEntity<?> userLastDays() {
        try {
            List<UsuarioDTO> lista = usuarioService.findLastUsers();
            if (lista.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron usuarios registrados en los últimos 30 días");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener usuarios recientes: " + e.getMessage());
        }
    }

    // Solo ADMIN puede filtrar usuarios por rol
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/userByRol")
    public ResponseEntity<?> userByRol(@RequestParam String nombre) {
        try {
            List<UsuarioDTO> lista = usuarioService.findByRolNombre(nombre);
            if (lista.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron usuarios con el rol: " + nombre);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al filtrar usuarios por rol: " + e.getMessage());
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

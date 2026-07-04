package pe.edu.upc.playcontrol.controllers;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
=======
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> fabrizzio-salvador
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;
import java.util.Map;
=======
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
>>>>>>> fabrizzio-salvador

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

<<<<<<< HEAD
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication auth) {
        try {
            return usuarioService.findByUsername(auth.getName())
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(buildErrorResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener perfil: " + e.getMessage());
        }
    }

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

    // Solo ADMIN cuenta los usuarios registrados en cada mes del año actual.
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

    // Solo ADMIN puede ver l
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/registrados-por-mes")
    public ResponseEntity<?> usuariosRegistradosPorMes() {
        return ResponseEntity.ok(usuarioService.usuariosRegistradosPorMes());
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
=======
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable UUID id) {
        return usuarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable UUID id, @RequestBody UsuarioDTO dto) {
        dto.setIdUsuario(id);
        return ResponseEntity.ok(usuarioService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userLastDays")
    public ResponseEntity<?> userLastDays(){
        ModelMapper mapper = new ModelMapper();
        List<UsuarioDTO> lista = usuarioService.findLastUsers().stream().map(u -> mapper.map(u, UsuarioDTO.class)).collect(Collectors.toList());

        if(!lista.isEmpty()){
            return ResponseEntity.ok(lista);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron usuarios registrados en los últimos 30 días");
        }
    }

    @GetMapping("/userByRol")
    public ResponseEntity<?> userByRol(@PathVariable String nombre){
        ModelMapper mapper = new ModelMapper();
        List<UsuarioDTO> lista = usuarioService.findByRolNombre(nombre)
                .stream()
                .map(user -> mapper.map(user, UsuarioDTO.class))
                .collect(Collectors.toList());

        if (!lista.isEmpty()) {
            return ResponseEntity.ok(lista);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron usuarios con el rol: " + nombre);
        }
    }

>>>>>>> fabrizzio-salvador
}

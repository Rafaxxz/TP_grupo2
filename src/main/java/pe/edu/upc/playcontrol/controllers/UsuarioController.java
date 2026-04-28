package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

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
    public ResponseEntity<?> save(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UsuarioDTO dto) {
        try {
            dto.setIdUsuario(id);
            return ResponseEntity.ok(usuarioService.save(dto));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para obtener hijos de un padre (US17, US18, US19, US20)
    // no funciona corregir: Falta endpoint para obtener puntos acumulados de usuario (US09, US10, US36)
    // no funciona corregir: Falta endpoint para actualizar puntos de usuario (US09, US10)
    // no funciona corregir: Falta endpoint para obtener estadísticas generales del usuario (US17, US33, US36)
    // no funciona corregir: Falta endpoint para obtener nivel de bienestar digital del usuario (US36)
    // no funciona corregir: Falta endpoint para buscar usuario por email o username (login/búsqueda)
    // no funciona corregir: Falta endpoint para vincular hijo con padre (US17, US18, US19)
}

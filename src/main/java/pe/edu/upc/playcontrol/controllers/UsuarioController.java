package pe.edu.upc.playcontrol.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IContenidoEducativoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/contenidos-educativos")
public class ContenidoEducativoController {

    @Autowired
    private IContenidoEducativoService contenidoEducativoService;

    @GetMapping
    public ResponseEntity<List<ContenidoEducativoDTO>> list() {
        return ResponseEntity.ok(contenidoEducativoService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<ContenidoEducativoDTO> insert(@RequestBody ContenidoEducativoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contenidoEducativoService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<ContenidoEducativoDTO> found = contenidoEducativoService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido educativo no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody ContenidoEducativoDTO dto) {
        Optional<ContenidoEducativoDTO> existing = contenidoEducativoService.listId(dto.getIdContenido());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido educativo no encontrado");
        }
        return ResponseEntity.ok(contenidoEducativoService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<ContenidoEducativoDTO> existing = contenidoEducativoService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido educativo no encontrado");
        }
        contenidoEducativoService.delete(id);
        return ResponseEntity.ok("Contenido educativo eliminado correctamente");
    }


    // - Falta endpoint para filtrar contenidos por tipo (articulo, video, guia, podcast) (US23, US24, US39)
    // - Falta endpoint para buscar contenidos por palabra clave o tema (US23, US26)
    // - Falta endpoint para obtener recursos descargables (US30)
    // - Falta endpoint para obtener contenidos ordenados por fecha de publicación (US25, US40)
}

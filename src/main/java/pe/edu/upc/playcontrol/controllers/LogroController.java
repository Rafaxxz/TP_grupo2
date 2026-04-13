package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private ILogroService logroService;

    // muestra todos los logros
    @GetMapping
    public ResponseEntity<List<LogroDTO>> getAll() {
        return ResponseEntity.ok(logroService.getAll());   // 200 OK
    }

    // muestra un logro por id
    // @PathVariable extrae el {id} de la URL y lo convierte a UUID automáticamente.
    @GetMapping("/{id}")
    public ResponseEntity<LogroDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(logroService.getById(id)); // 200 OK
    }

    // para crear un nuevo recurso
    @PostMapping
    public ResponseEntity<LogroDTO> save(@RequestBody LogroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroService.save(dto)); // 201 Created
    }

    // para reemplazar un recurso existente completo
    @PutMapping("/{id}")
    public ResponseEntity<LogroDTO> update(@PathVariable UUID id, @RequestBody LogroDTO dto) {
        return ResponseEntity.ok(logroService.update(id, dto)); // 200 OK
    }

    // para eliminar un recurso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logroService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

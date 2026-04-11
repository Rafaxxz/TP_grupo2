package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.UUID;

// @RestController = @Controller + @ResponseBody
// Le dice a Spring que esta clase maneja peticiones HTTP y que
// cada método devuelve datos directamente como JSON (no vistas HTML).
@RestController

// @RequestMapping define la ruta base para todos los endpoints de este controlador.
// Todos los métodos aquí responden bajo /api/logros
@RequestMapping("/api/logros")
public class LogroController {

    // Inyectamos la INTERFAZ, no la implementación concreta.
    // Si mañana cambias LogroServiceImplement por otra clase, este controlador no cambia.
    @Autowired
    private ILogroService logroService;

    // @GetMapping = HTTP GET → para leer/listar recursos.
    // ResponseEntity<T> nos da control sobre el código de estado HTTP de la respuesta.
    @GetMapping
    public ResponseEntity<List<LogroDTO>> getAll() {
        return ResponseEntity.ok(logroService.getAll());   // 200 OK
    }

    // @PathVariable extrae el {id} de la URL y lo convierte a UUID automáticamente.
    @GetMapping("/{id}")
    public ResponseEntity<LogroDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(logroService.getById(id)); // 200 OK
    }

    // @PostMapping = HTTP POST → para crear un nuevo recurso.
    // @RequestBody convierte el JSON del cuerpo de la petición al DTO automáticamente.
    // HTTP 201 Created es el código correcto para recursos recién creados.
    @PostMapping
    public ResponseEntity<LogroDTO> save(@RequestBody LogroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(logroService.save(dto)); // 201 Created
    }

    // @PutMapping = HTTP PUT → para reemplazar un recurso existente completo.
    @PutMapping("/{id}")
    public ResponseEntity<LogroDTO> update(@PathVariable UUID id, @RequestBody LogroDTO dto) {
        return ResponseEntity.ok(logroService.update(id, dto)); // 200 OK
    }

    // @DeleteMapping = HTTP DELETE → para eliminar un recurso.
    // 204 No Content: éxito sin cuerpo de respuesta (no hay nada que devolver).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logroService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

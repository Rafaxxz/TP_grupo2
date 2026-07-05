package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.ReglaJuegoDTO;
import pe.edu.upc.playcontrol.dtos.ReglaJuegoRequest;
import pe.edu.upc.playcontrol.servicesinterfaces.ReglaJuegoService;

import java.util.List;

@RestController
@RequestMapping("/reglas-juego")
public class ReglaJuegoController {

    private final ReglaJuegoService service;

    public ReglaJuegoController(ReglaJuegoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReglaJuegoRequest req) {
        if (req.getHijoId() == null || req.getNombre() == null || req.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("hijoId y nombre son obligatorios");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ReglaJuegoRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // El HIJO ve sus propias reglas; PADRE y ADMIN pueden ver las de cualquiera
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/hijo/{hijoId}")
    public ResponseEntity<List<ReglaJuegoDTO>> listarPorHijo(@PathVariable Integer hijoId) {
        return ResponseEntity.ok(service.listarPorHijo(hijoId));
    }
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/canjes")
public class CanjeRecompensaController {

    @Autowired
    private ICanjeRecompensaService canjeRecompensaService;

    //retorna todos los canjes
    @GetMapping
    public ResponseEntity<List<CanjeRecompensaDTO>> getAll() {
        return ResponseEntity.ok(canjeRecompensaService.getAll());
    }

    //retorna un canje por id
    @GetMapping("/{id}")
    public ResponseEntity<CanjeRecompensaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(canjeRecompensaService.getById(id));
    }

    //todos los canjes realizados por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CanjeRecompensaDTO>> getByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(canjeRecompensaService.getByUsuarioId(usuarioId));
    }

    //crea un nuevo canje
    @PostMapping
    public ResponseEntity<CanjeRecompensaDTO> save(@RequestBody CanjeRecompensaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(canjeRecompensaService.save(dto));
    }

    //elimina un canje por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        canjeRecompensaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

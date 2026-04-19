package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.MensajeDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IMensajeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @GetMapping
    public ResponseEntity<List<MensajeDTO>> getAll() {
        return ResponseEntity.ok(mensajeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO> getById(@PathVariable UUID id) {
        return mensajeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MensajeDTO> save(@RequestBody MensajeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO> update(@PathVariable UUID id, @RequestBody MensajeDTO dto) {
        dto.setIdMensaje(id);
        return ResponseEntity.ok(mensajeService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        mensajeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // no funciona corregir: Falta endpoint para obtener mensajes por usuario remitente (US22, US37)
    // no funciona corregir: Falta endpoint para obtener mensajes por usuario destinatario (US22, US37)
    // no funciona corregir: Falta endpoint para marcar mensaje como leído (US22)
    // no funciona corregir: Falta endpoint para obtener conversación entre dos usuarios (US37)
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.MensajeDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IMensajeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Aquí se exponen los endpoints REST para la tabla mensaje
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @GetMapping
    public ResponseEntity<List<MensajeDTO>> list() {
        return ResponseEntity.ok(mensajeService.list());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<MensajeDTO> insert(@RequestBody MensajeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.insert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable UUID id) {
        Optional<MensajeDTO> found = mensajeService.listId(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody MensajeDTO dto) {
        Optional<MensajeDTO> existing = mensajeService.listId(dto.getIdMensaje());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado");
        }
        return ResponseEntity.ok(mensajeService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<MensajeDTO> existing = mensajeService.listId(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado");
        }
        mensajeService.delete(id);
        return ResponseEntity.ok("Mensaje eliminado correctamente");
    }

    // Filtro simple: trae mensajes enviados por un remitente
    @GetMapping("/por-remitente/{remitenteId}")
    public ResponseEntity<List<MensajeDTO>> listByRemitenteId(@PathVariable UUID remitenteId) {
        return ResponseEntity.ok(mensajeService.listByRemitenteId(remitenteId));
    }

    // Query de decisión: mensajes no leídos recibidos por un usuario
    @GetMapping("/no-leidos/{destinatarioId}")
    public ResponseEntity<List<MensajeDTO>> listNoLeidosByDestinatarioId(@PathVariable UUID destinatarioId) {
        return ResponseEntity.ok(mensajeService.listNoLeidosByDestinatarioId(destinatarioId));
    }

    // consultar validez: los siguientes van más allá del alcance de Semana 04 (requieren lógica de negocio compleja)
    // - Falta endpoint para marcar mensaje como leído (US22)
    // - Falta endpoint para obtener conversación entre dos usuarios (US37)
}

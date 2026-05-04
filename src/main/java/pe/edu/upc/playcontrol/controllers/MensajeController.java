package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.MensajeDTO;
import pe.edu.upc.playcontrol.servicesinterfaces.IMensajeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    // Solo ADMIN ve el listado completo de todos los mensajes del sistema
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(mensajeService.list());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de mensajes: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden enviar mensajes
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> insert(@RequestBody MensajeDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.insert(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el mensaje: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el detalle de un mensaje
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        try {
            Optional<MensajeDTO> found = mensajeService.listId(id);
            if (found.isPresent()) {
                return ResponseEntity.ok(found.get());
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    "No se encontró el mensaje con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar el mensaje: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden modificar un mensaje (ej: marcar como leído)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @PutMapping("/actualiza")
    public ResponseEntity<?> update(@RequestBody MensajeDTO dto) {
        try {
            Optional<MensajeDTO> existing = mensajeService.listId(dto.getIdMensaje());
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el mensaje con id: " + dto.getIdMensaje());
            }
            return ResponseEntity.ok(mensajeService.update(dto));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el mensaje: " + e.getMessage());
        }
    }

    // Solo ADMIN puede eliminar mensajes
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<MensajeDTO> existing = mensajeService.listId(id);
            if (existing.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el mensaje con id: " + id);
            }
            mensajeService.delete(id);
            return ResponseEntity.ok("Mensaje eliminado correctamente");
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el mensaje: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver mensajes enviados por un remitente
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/por-remitente/{remitenteId}")
    public ResponseEntity<?> listByRemitenteId(@PathVariable Integer remitenteId) {
        try {
            List<MensajeDTO> result = mensajeService.listByRemitenteId(remitenteId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron mensajes del remitente con id: " + remitenteId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener mensajes del remitente: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver sus mensajes no leídos
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/no-leidos/{destinatarioId}")
    public ResponseEntity<?> listNoLeidosByDestinatarioId(@PathVariable Integer destinatarioId) {
        try {
            List<MensajeDTO> result = mensajeService.listNoLeidosByDestinatarioId(destinatarioId);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No hay mensajes no leídos para el usuario con id: " + destinatarioId);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener mensajes no leídos: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver la conversación entre dos usuarios
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/conversacion")
    public ResponseEntity<?> findConversacionBetweenUsers(
            @RequestParam Integer usuarioA,
            @RequestParam Integer usuarioB) {
        try {
            List<MensajeDTO> result = mensajeService.findConversacionBetweenUsers(usuarioA, usuarioB);
            if (result.isEmpty()) {
                return buildErrorResponse(HttpStatus.NOT_FOUND,
                        "No se encontraron mensajes entre los usuarios indicados");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la conversación: " + e.getMessage());
        }
    }

    // ADMIN, PADRE e HIJO pueden ver el resumen de mensajes no leídos agrupados por remitente
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
    @GetMapping("/resumen-no-leidos/{usuarioId}")
    public ResponseEntity<?> resumenNoLeidosPorRemitente(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(mensajeService.resumenNoLeidosPorRemitente(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el resumen de mensajes no leídos: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
}

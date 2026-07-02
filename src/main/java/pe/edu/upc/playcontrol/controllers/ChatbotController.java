package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.dtos.ChatHistorialDTO;
import pe.edu.upc.playcontrol.dtos.ChatMensajeDTO;
import pe.edu.upc.playcontrol.dtos.ChatRespuestaDTO;
import pe.edu.upc.playcontrol.servicesimplements.GeminiRateLimitException;
import pe.edu.upc.playcontrol.servicesinterfaces.IChatbotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final IChatbotService service;

    public ChatbotController(IChatbotService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('PADRE', 'HIJO')")
    @PostMapping("/preguntar")
    public ResponseEntity<?> preguntar(@RequestBody ChatMensajeDTO request) {
        if (request == null || request.getIdUsuario() == null) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El idUsuario es obligatorio");
        }
        if (request.getMensaje() == null || request.getMensaje().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "El mensaje no puede estar vacío");
        }
        try {
            ChatRespuestaDTO respuesta = service.preguntar(request);
            return ResponseEntity.ok(respuesta);
        } catch (GeminiRateLimitException e) {
            // Rate limit / cuota de Gemini excedida: 503 con mensaje claro (no 500 genérico)
            return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,
                    "El asistente está muy solicitado, intenta de nuevo en unos minutos.");
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalStateException e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al procesar la consulta: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('PADRE', 'HIJO')")
    @GetMapping("/historial/{idUsuario}")
    public ResponseEntity<?> historial(@PathVariable Integer idUsuario) {
        try {
            List<ChatHistorialDTO> result = service.historial(idUsuario);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener el historial: " + e.getMessage());
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

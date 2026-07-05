package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.servicesinterfaces.IFamiliaService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/familia")
public class FamiliaController {

    @Autowired
    private IFamiliaService familiaService;

    @PreAuthorize("hasAuthority('PADRE')")
    @PostMapping("/vincular")
    public ResponseEntity<?> vincular(@RequestBody Map<String, String> body, Authentication auth) {
        try {
            String hijoEmail = body.get("email");
            if (hijoEmail == null || hijoEmail.isBlank()) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "El email del hijo es obligatorio");
            }
            return ResponseEntity.ok(familiaService.vincularHijo(auth.getName(), hijoEmail.trim()));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalStateException e) {
            return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al vincular: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('PADRE')")
    @GetMapping("/hijos")
    public ResponseEntity<?> listarHijos(Authentication auth) {
        try {
            return ResponseEntity.ok(familiaService.listarHijos(auth.getName()));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar hijos: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('PADRE')")
    @DeleteMapping("/hijos/{id}")
    public ResponseEntity<?> desvincular(@PathVariable Integer id, Authentication auth) {
        try {
            familiaService.desvincularHijo(auth.getName(), id);
            return ResponseEntity.ok(Map.of("message", "Hijo desvinculado correctamente"));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al desvincular: " + e.getMessage());
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

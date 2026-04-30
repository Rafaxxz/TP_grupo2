package pe.edu.upc.playcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sesiones")
public class SesionJuegoController {

    private final SesionJuegoService service;

    public SesionJuegoController(SesionJuegoService service) {
        this.service = service;
    }

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar porque las relaciones @ManyToOne (usuario, juego) requieren objetos completos, no IDs. Ya existe SesionJuegoDTO, implementar conversión en service.
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody SesionJuego sesionJuego) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(sesionJuego));
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear sesión: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON. Ya existe SesionJuegoDTO, usarlo.
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesiones: " + e.getMessage());
        }
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON. Ya existe SesionJuegoDTO, usarlo.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        try {
            SesionJuego sesion = service.buscarPorId(id);
            if (sesion != null) {
                return ResponseEntity.ok(sesion);
            }
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Sesión no encontrada con id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener sesión: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<?> historialPorUsuario(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(service.historialPorUsuario(usuarioId));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener historial: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    // no funciona corregir: Falta endpoint PUT /{id} para actualizar sesión (finalizar sesión activa) (US01)
    // no funciona corregir: Falta endpoint para obtener sesiones por usuario (US01, US17, US33, US43)
    // no funciona corregir: Falta endpoint para obtener sesiones por fecha o rango de fechas (US01, US33, US43)
    // no funciona corregir: Falta endpoint para obtener sesiones activas de un usuario (US01, US02)
    // no funciona corregir: Falta endpoint para obtener tiempo total jugado por usuario (diario/semanal/mensual) (US01, US02, US04, US05, US17, US33, US43)
    // no funciona corregir: Falta endpoint para obtener estadísticas de tiempo de juego por juego (US01, US33)
    // no funciona corregir: Falta endpoint para obtener reporte mensual de sesiones (US33)
    // no funciona corregir: Falta endpoint para obtener panel de rendimiento semanal (US43)
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar sesión
}

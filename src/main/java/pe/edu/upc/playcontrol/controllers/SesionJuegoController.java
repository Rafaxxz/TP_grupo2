package pe.edu.upc.playcontrol.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.util.List;
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
    public SesionJuego guardar(@RequestBody SesionJuego sesionJuego) {
        return service.guardar(sesionJuego);
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON. Ya existe SesionJuegoDTO, usarlo.
    @GetMapping
    public List<SesionJuego> listar() {
        return service.listar();
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON. Ya existe SesionJuegoDTO, usarlo.
    @GetMapping("/{id}")
    public SesionJuego buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
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

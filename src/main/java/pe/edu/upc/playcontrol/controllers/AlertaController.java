package pe.edu.upc.playcontrol.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar datos porque las relaciones @ManyToOne (usuario, sesionJuego) requieren objetos completos, no solo IDs. Crear AlertaDTO y convertir en el service.
    @PostMapping
    public Alerta guardar(@RequestBody Alerta alerta) {
        return service.guardar(alerta);
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON o exponer datos innecesarios. Usar AlertaDTO.
    @GetMapping
    public List<Alerta> listar() {
        return service.listar();
    }

    // no funciona corregir: Este endpoint retorna Entity con relaciones @ManyToOne, puede causar referencias circulares en JSON o exponer datos innecesarios. Usar AlertaDTO.
    @GetMapping("/{id}")
    public Alerta buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    // no funciona corregir: Falta endpoint para obtener alertas no leídas por usuario (US02, US08)
    // no funciona corregir: Falta endpoint para marcar alerta como leída (US06, US08)
    // no funciona corregir: Falta endpoint para obtener alertas por usuario (US02, US17, US18)
    // no funciona corregir: Falta endpoint para filtrar alertas por periodo/fecha (US08)
    // no funciona corregir: Falta endpoint para actualizar alerta
    // no funciona corregir: Falta endpoint para eliminar alerta
}
package pe.edu.upc.playcontrol.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/limites")
public class LimiteTiempoController {

    private final LimiteTiempoService service;

    public LimiteTiempoController(LimiteTiempoService service) {
        this.service = service;
    }

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar porque la relación @ManyToOne (usuario) requiere objeto completo, no ID. Ya existe LimiteTiempoDTO, implementar conversión en service.
    @PostMapping
    public LimiteTiempo guardar(@RequestBody LimiteTiempo limiteTiempo) {
        return service.guardar(limiteTiempo);
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe LimiteTiempoDTO, usarlo.
    @GetMapping
    public List<LimiteTiempo> listar() {
        return service.listar();
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe LimiteTiempoDTO, usarlo.
    @GetMapping("/{id}")
    public LimiteTiempo buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    // no funciona corregir: Falta endpoint PUT /{id} para actualizar límite (US04, US05, US19)
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar límite
    // no funciona corregir: Falta endpoint para obtener límites por usuario (US04, US05, US17, US19)
    // no funciona corregir: Falta endpoint para obtener límite activo de un usuario por tipo (diario/semanal) (US04, US05)
    // no funciona corregir: Falta endpoint para verificar si usuario excedió límite (US02, US18)
    // no funciona corregir: Falta endpoint para activar/desactivar bloqueo temporal (US07)
}
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

    @PostMapping
    public Alerta guardar(@RequestBody Alerta alerta) {
        return service.guardar(alerta);
    }

    @GetMapping
    public List<Alerta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Alerta buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}
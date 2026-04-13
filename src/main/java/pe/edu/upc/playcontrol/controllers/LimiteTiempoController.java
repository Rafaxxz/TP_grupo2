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

    @PostMapping
    public LimiteTiempo guardar(@RequestBody LimiteTiempo limiteTiempo) {
        return service.guardar(limiteTiempo);
    }

    @GetMapping
    public List<LimiteTiempo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public LimiteTiempo buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}
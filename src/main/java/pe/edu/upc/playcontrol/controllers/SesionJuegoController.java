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

    @PostMapping
    public SesionJuego guardar(@RequestBody SesionJuego sesionJuego) {
        return service.guardar(sesionJuego);
    }

    @GetMapping
    public List<SesionJuego> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SesionJuego buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}

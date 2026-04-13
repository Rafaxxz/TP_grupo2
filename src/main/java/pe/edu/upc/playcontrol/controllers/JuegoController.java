package pe.edu.upc.playcontrol.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

    private final JuegoService service;

    public JuegoController(JuegoService service) {
        this.service = service;
    }

    @PostMapping
    public Juego guardar(@RequestBody Juego juego) {
        return service.guardar(juego);
    }

    @GetMapping
    public List<Juego> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Juego buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}
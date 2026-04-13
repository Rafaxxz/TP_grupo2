package pe.edu.upc.playcontrol.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.List;

@RestController
@RequestMapping("/categorias-juego")
public class CategoriaJuegoController {

    private final CategoriaJuegoService service;

    public CategoriaJuegoController(CategoriaJuegoService service) {
        this.service = service;
    }

    @PostMapping
    public CategoriaJuego guardar(@RequestBody CategoriaJuego categoriaJuego) {
        return service.guardar(categoriaJuego);
    }

    @GetMapping
    public List<CategoriaJuego> listar() {
        return service.listar();
    }
}
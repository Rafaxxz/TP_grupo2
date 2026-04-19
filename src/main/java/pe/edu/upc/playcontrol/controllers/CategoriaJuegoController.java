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

    // no funciona corregir: Recomendado usar CategoriaJuegoDTO en lugar de Entity para consistencia con el resto del proyecto, aunque esta entidad no tiene relaciones complejas.
    @PostMapping
    public CategoriaJuego guardar(@RequestBody CategoriaJuego categoriaJuego) {
        return service.guardar(categoriaJuego);
    }

    // no funciona corregir: Recomendado usar CategoriaJuegoDTO en lugar de Entity para consistencia con el resto del proyecto.
    @GetMapping
    public List<CategoriaJuego> listar() {
        return service.listar();
    }

    // no funciona corregir: Falta endpoint GET /{id} para buscar categoría por ID
    // no funciona corregir: Falta endpoint PUT /{id} para actualizar categoría
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar categoría
}
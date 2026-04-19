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

    // no funciona corregir: Este endpoint usa Entity en lugar de DTO, causará error al insertar porque la relación @ManyToOne (categoriaJuego) requiere objeto completo, no ID. Ya existe JuegoDTO, implementar conversión en service.
    @PostMapping
    public Juego guardar(@RequestBody Juego juego) {
        return service.guardar(juego);
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe JuegoDTO, usarlo.
    @GetMapping
    public List<Juego> listar() {
        return service.listar();
    }

    // no funciona corregir: Este endpoint retorna Entity con relación @ManyToOne, puede causar referencias circulares en JSON. Ya existe JuegoDTO, usarlo.
    @GetMapping("/{id}")
    public Juego buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    // no funciona corregir: Falta endpoint PUT /{id} para actualizar juego
    // no funciona corregir: Falta endpoint DELETE /{id} para eliminar juego
    // no funciona corregir: Falta endpoint para buscar juegos por categoría (para organización)
    // no funciona corregir: Falta endpoint para buscar juegos por usuario (juegos que juega un usuario)
}
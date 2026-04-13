package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.CategoriaJuego;

import java.util.List;

public interface CategoriaJuegoService {
    CategoriaJuego guardar(CategoriaJuego categoriaJuego);
    List<CategoriaJuego> listar();
}
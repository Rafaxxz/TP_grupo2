package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;

public interface JuegoService {
    Juego guardar(Juego juego);
    List<Juego> listar();
    Juego buscarPorId(Integer id);
    List<JuegoDTO> buscarPorPlataforma(String plataforma);

    List<JuegoDTO> buscarPorCategoria(Integer idCategoria);
}

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;
import java.util.UUID;

public interface JuegoService {
    Juego guardar(Juego juego);
    List<Juego> listar();
    Juego buscarPorId(UUID id);
}
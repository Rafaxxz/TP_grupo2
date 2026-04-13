package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.util.List;
import java.util.UUID;

public interface SesionJuegoService {
    SesionJuego guardar(SesionJuego sesionJuego);
    List<SesionJuego> listar();
    SesionJuego buscarPorId(UUID id);
}
package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.util.List;

public interface SesionJuegoService {
    SesionJuego guardar(SesionJuego sesionJuego);
    List<SesionJuego> listar();
    SesionJuego buscarPorId(Integer id);
    List<SesionJuego> historialPorUsuario(Integer usuarioId);
}

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.time.LocalDate;
import java.util.List;

public interface SesionJuegoService {
    SesionJuego guardar(SesionJuego sesionJuego);
    List<SesionJuego> listar();
    SesionJuego buscarPorId(Integer id);
    List<SesionJuego> historialPorUsuario(Integer usuarioId);
    List<SesionJuegoDTO> buscarPorUsuario(Integer usuarioId);
    List<SesionJuegoDTO> buscarPorFecha(LocalDate fecha);
}

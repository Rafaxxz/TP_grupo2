package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
<<<<<<< HEAD
import pe.edu.upc.playcontrol.entities.SesionJuego;
import java.time.LocalDate;
=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SesionJuegoService {
<<<<<<< HEAD
    SesionJuego guardar(SesionJuego sesionJuego);
    List<SesionJuego> listar();
    SesionJuego buscarPorId(Integer id);
    List<SesionJuego> historialPorUsuario(Integer usuarioId);

    List<SesionJuegoDTO> buscarPorUsuario(UUID usuarioId);

    List<SesionJuegoDTO> buscarPorFecha(LocalDate fecha);
}
=======
    List<SesionJuegoDTO> getAll();
    Optional<SesionJuegoDTO> getById(UUID id);
    SesionJuegoDTO save(SesionJuegoDTO dto);
    void delete(UUID id);
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.JuegoDTO;
<<<<<<< HEAD
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;

public interface JuegoService {
    Juego guardar(Juego juego);
    List<Juego> listar();
    Juego buscarPorId(Integer id);
    List<JuegoDTO> buscarPorPlataforma(String plataforma);

    List<JuegoDTO> buscarPorCategoria(Integer idCategoria);
}
=======

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuegoService {
    List<JuegoDTO> getAll();
    Optional<JuegoDTO> getById(UUID id);
    JuegoDTO save(JuegoDTO dto);
    void delete(UUID id);
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

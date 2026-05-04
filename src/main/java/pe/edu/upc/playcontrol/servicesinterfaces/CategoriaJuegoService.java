package pe.edu.upc.playcontrol.servicesinterfaces;
<<<<<<< HEAD
import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
=======

import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;

>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import java.util.List;
import java.util.Optional;

public interface CategoriaJuegoService {
    List<CategoriaJuegoDTO> getAll();
    Optional<CategoriaJuegoDTO> getById(Integer id);
    CategoriaJuegoDTO save(CategoriaJuegoDTO dto);
    void delete(Integer id);
<<<<<<< HEAD

    List<CategoriaJuegoDTO> buscarPorNombre(String nombre);
    boolean existePorNombre(String nombre);

=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
}
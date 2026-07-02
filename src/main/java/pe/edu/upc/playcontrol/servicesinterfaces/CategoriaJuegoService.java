package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriaJuegoService {
    List<CategoriaJuegoDTO> getAll();
    Optional<CategoriaJuegoDTO> getById(Integer id);
    CategoriaJuegoDTO save(CategoriaJuegoDTO dto);
    void delete(Integer id);
    List<CategoriaJuegoDTO> buscarPorNombre(String nombre);
    boolean existePorNombre(String nombre);
}

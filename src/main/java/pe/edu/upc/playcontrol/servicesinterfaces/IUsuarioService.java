package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<UsuarioDTO> getAll();
    Optional<UsuarioDTO> getById(Integer id);
    UsuarioDTO save(UsuarioDTO dto);
    void delete(Integer id);
    List<UsuarioDTO> findLastUsers();
    List<UsuarioDTO> findByRolNombre(String nombre);
}

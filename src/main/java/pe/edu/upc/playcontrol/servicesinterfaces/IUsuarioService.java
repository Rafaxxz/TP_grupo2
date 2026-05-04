package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsuarioService {
    List<UsuarioDTO> getAll();
    Optional<UsuarioDTO> getById(UUID id);
    UsuarioDTO save(UsuarioDTO dto);
    void delete(UUID id);
    List<Usuario>findLastUsers();
    List<Usuario> findByRolNombre(String nombre);
}

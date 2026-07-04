package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
<<<<<<< HEAD

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUsuarioService {
    List<UsuarioDTO> getAll();
    Optional<UsuarioDTO> getById(Integer id);
    Optional<UsuarioDTO> findByUsername(String username);
    UsuarioDTO save(UsuarioDTO dto);
    void delete(Integer id);
    List<UsuarioDTO> findLastUsers();
    List<UsuarioDTO> findByRolNombre(String nombre);
    List<Map<String, Object>> usuariosRegistradosPorMes();
=======
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
>>>>>>> fabrizzio-salvador
}

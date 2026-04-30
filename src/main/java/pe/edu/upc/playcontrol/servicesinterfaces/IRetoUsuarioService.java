package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IRetoUsuarioService {
    List<RetoUsuarioDTO> getAll();
    Optional<RetoUsuarioDTO> getById(Integer id);
    RetoUsuarioDTO save(RetoUsuarioDTO dto);
    void delete(Integer id);
}

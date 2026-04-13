package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRetoUsuarioService {
    List<RetoUsuarioDTO> getAll();
    Optional<RetoUsuarioDTO> getById(UUID id);
    RetoUsuarioDTO save(RetoUsuarioDTO dto);
    void delete(UUID id);
}

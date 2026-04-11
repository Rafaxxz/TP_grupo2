package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;

import java.util.List;
import java.util.UUID;

public interface ILogroUsuarioService {

    List<LogroUsuarioDTO> getAll();

    LogroUsuarioDTO getById(UUID id);

    List<LogroUsuarioDTO> getByUsuarioId(UUID usuarioId);

    LogroUsuarioDTO save(LogroUsuarioDTO dto);

    void delete(UUID id);
}

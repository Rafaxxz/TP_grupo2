package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;

import java.util.List;

public interface ILogroUsuarioService {

    List<LogroUsuarioDTO> getAll();

    LogroUsuarioDTO getById(Integer id);

    List<LogroUsuarioDTO> getByUsuarioId(Integer usuarioId);

    LogroUsuarioDTO save(LogroUsuarioDTO dto);

    void delete(Integer id);
}

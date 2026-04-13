package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroDTO;

import java.util.List;
import java.util.UUID;

public interface ILogroService {

    List<LogroDTO> getAll();

    LogroDTO getById(UUID id);

    LogroDTO save(LogroDTO dto);

    LogroDTO update(UUID id, LogroDTO dto);

    void delete(UUID id);
}

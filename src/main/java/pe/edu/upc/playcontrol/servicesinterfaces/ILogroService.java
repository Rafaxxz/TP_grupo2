package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroDTO;

import java.util.List;

public interface ILogroService {

    List<LogroDTO> getAll();

    LogroDTO getById(Integer id);

    LogroDTO save(LogroDTO dto);

    LogroDTO update(Integer id, LogroDTO dto);

    void delete(Integer id);
}

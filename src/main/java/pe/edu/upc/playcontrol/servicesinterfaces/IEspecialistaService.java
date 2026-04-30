package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.EspecialistaDTO;

import java.util.List;
import java.util.Optional;

public interface IEspecialistaService {
    List<EspecialistaDTO> getAll();
    Optional<EspecialistaDTO> getById(Integer id);
    EspecialistaDTO save(EspecialistaDTO dto);
    void delete(Integer id);
    List<EspecialistaDTO> getByEspecialidad(String especialidad);
}

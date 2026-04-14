package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RolDTO;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<RolDTO> getAll();
    Optional<RolDTO> getById(Integer id);
    RolDTO save(RolDTO dto);
    void delete(Integer id);
}

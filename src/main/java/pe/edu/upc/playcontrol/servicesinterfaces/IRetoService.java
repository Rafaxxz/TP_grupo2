package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoDTO;

import java.util.List;
import java.util.Optional;

public interface IRetoService {
    List<RetoDTO> getAll();

    Optional<RetoDTO> getById(Integer id);
    RetoDTO save(RetoDTO dto);

    void delete(Integer id);
}

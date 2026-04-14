package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRetoService {
    List<RetoDTO> getAll();

    Optional<RetoDTO> getById(UUID id);
    RetoDTO save(RetoDTO dto);

    void delete(UUID id);
}

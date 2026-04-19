package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRetoService {
    List<RetoDTO> list();
    RetoDTO insert(RetoDTO dto);
    RetoDTO update(RetoDTO dto);
    Optional<RetoDTO> listId(UUID id);
    void delete(UUID id);
}

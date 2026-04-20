package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.JuegoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuegoService {
    List<JuegoDTO> getAll();
    Optional<JuegoDTO> getById(UUID id);
    JuegoDTO save(JuegoDTO dto);
    void delete(UUID id);
}
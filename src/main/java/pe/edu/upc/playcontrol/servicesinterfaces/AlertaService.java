package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.AlertaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlertaService {
    List<AlertaDTO> getAll();
    Optional<AlertaDTO> getById(UUID id);
    AlertaDTO save(AlertaDTO dto);
    void delete(UUID id);
}
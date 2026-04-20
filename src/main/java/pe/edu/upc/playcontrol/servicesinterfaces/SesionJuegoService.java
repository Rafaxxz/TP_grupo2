package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SesionJuegoService {
    List<SesionJuegoDTO> getAll();
    Optional<SesionJuegoDTO> getById(UUID id);
    SesionJuegoDTO save(SesionJuegoDTO dto);
    void delete(UUID id);
}
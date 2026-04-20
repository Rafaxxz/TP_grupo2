package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LimiteTiempoService {
    List<LimiteTiempoDTO> getAll();
    Optional<LimiteTiempoDTO> getById(UUID id);
    LimiteTiempoDTO save(LimiteTiempoDTO dto);
    void delete(UUID id);
}
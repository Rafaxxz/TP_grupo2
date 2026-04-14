package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.MensajeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMensajeService {
    List<MensajeDTO> getAll();
    Optional<MensajeDTO> getById(UUID id);
    MensajeDTO save(MensajeDTO dto);
    void delete(UUID id);
}

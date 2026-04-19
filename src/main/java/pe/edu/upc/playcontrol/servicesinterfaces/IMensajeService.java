package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.MensajeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMensajeService {
    List<MensajeDTO> list();
    MensajeDTO insert(MensajeDTO dto);
    MensajeDTO update(MensajeDTO dto);
    Optional<MensajeDTO> listId(UUID id);
    void delete(UUID id);
}

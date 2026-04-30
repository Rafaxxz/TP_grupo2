package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.MensajeDTO;

import java.util.List;
import java.util.Optional;

public interface IMensajeService {
    List<MensajeDTO> getAll();
    Optional<MensajeDTO> getById(Integer id);
    MensajeDTO save(MensajeDTO dto);
    void delete(Integer id);
}

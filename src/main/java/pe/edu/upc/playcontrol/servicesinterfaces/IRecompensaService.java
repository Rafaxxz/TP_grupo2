package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RecompensaDTO;

import java.util.List;
import java.util.UUID;

public interface IRecompensaService {

    List<RecompensaDTO> getAll();

    RecompensaDTO getById(UUID id);

    RecompensaDTO save(RecompensaDTO dto);

    RecompensaDTO update(UUID id, RecompensaDTO dto);

    void delete(UUID id);
}

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;

import java.util.List;
import java.util.UUID;

public interface ICanjeRecompensaService {

    List<CanjeRecompensaDTO> getAll();

    CanjeRecompensaDTO getById(UUID id);

    List<CanjeRecompensaDTO> getByUsuarioId(UUID usuarioId);

    CanjeRecompensaDTO save(CanjeRecompensaDTO dto);

    void delete(UUID id);
}

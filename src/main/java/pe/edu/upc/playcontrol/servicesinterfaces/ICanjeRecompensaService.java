package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;

import java.util.List;

public interface ICanjeRecompensaService {

    //devuelve todos los canjes de recompensa como dto
    List<CanjeRecompensaDTO> getAll();

    // devuelve un canje de recompensa por id
    CanjeRecompensaDTO getById(Integer id);

    // devuelve todos los canjes de recompensa por un usuario
    List<CanjeRecompensaDTO> getByUsuarioId(Integer usuarioId);

    // crea un nuevo canje de recompensa
    CanjeRecompensaDTO save(CanjeRecompensaDTO dto);

    // elimina un canje de recompensa por id
    void delete(Integer id);
}

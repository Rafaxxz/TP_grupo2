package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RecompensaDTO;

import java.util.List;

public interface IRecompensaService {

    List<RecompensaDTO> getAll();

    RecompensaDTO getById(Integer id);

    RecompensaDTO save(RecompensaDTO dto);

    RecompensaDTO update(Integer id, RecompensaDTO dto);

    void delete(Integer id);
}

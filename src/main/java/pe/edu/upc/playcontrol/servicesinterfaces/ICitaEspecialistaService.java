package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;

import java.util.List;
import java.util.Optional;

public interface ICitaEspecialistaService {
    List<CitaEspecialistaDTO> getAll();
    Optional<CitaEspecialistaDTO> getById(Integer id);
    CitaEspecialistaDTO save(CitaEspecialistaDTO dto);
    void delete(Integer id);
    List<CitaEspecialistaDTO> getByUsuarioId(Integer usuarioId);
}

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICitaEspecialistaService {
    List<CitaEspecialistaDTO> list();
    CitaEspecialistaDTO insert(CitaEspecialistaDTO dto);
    CitaEspecialistaDTO update(CitaEspecialistaDTO dto);
    Optional<CitaEspecialistaDTO> listId(UUID id);
    void delete(UUID id);
}

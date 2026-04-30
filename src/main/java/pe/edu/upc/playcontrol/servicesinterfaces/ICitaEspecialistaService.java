package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICitaEspecialistaService {
    List<CitaEspecialistaDTO> getAll();
    Optional<CitaEspecialistaDTO> getById(UUID id);
    CitaEspecialistaDTO save(CitaEspecialistaDTO dto);
    void delete(UUID id);
    List<CitaEspecialistaDTO> getByUsuarioId(UUID usuarioId);
}

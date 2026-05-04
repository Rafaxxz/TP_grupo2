package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.EspecialistaDTO;
import pe.edu.upc.playcontrol.entities.Especialista;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEspecialistaService {
    List<EspecialistaDTO> getAll();
    Optional<EspecialistaDTO> getById(UUID id);
    EspecialistaDTO save(EspecialistaDTO dto);
    void delete(UUID id);
    List<Especialista> findByVerificateTrue();
}

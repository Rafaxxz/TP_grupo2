package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContenidoEducativoService {
    List<ContenidoEducativoDTO> getAll();
    Optional<ContenidoEducativoDTO> getById(UUID id);
    ContenidoEducativoDTO save(ContenidoEducativoDTO dto);
    void delete(UUID id);
}

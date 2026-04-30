package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;

import java.util.List;
import java.util.Optional;

public interface IContenidoEducativoService {
    List<ContenidoEducativoDTO> getAll();
    Optional<ContenidoEducativoDTO> getById(Integer id);
    ContenidoEducativoDTO save(ContenidoEducativoDTO dto);
    void delete(Integer id);
}

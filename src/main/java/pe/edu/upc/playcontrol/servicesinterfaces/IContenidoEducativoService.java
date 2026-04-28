package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContenidoEducativoService {
    List<ContenidoEducativoDTO> list();
    ContenidoEducativoDTO insert(ContenidoEducativoDTO dto);
    ContenidoEducativoDTO update(ContenidoEducativoDTO dto);
    Optional<ContenidoEducativoDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: contenidos filtrados por tipo (articulo, video, guia, podcast)
    List<ContenidoEducativoDTO> listByTipo(String tipo);
}

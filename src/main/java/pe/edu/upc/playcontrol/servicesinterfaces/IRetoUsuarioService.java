package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRetoUsuarioService {
    List<RetoUsuarioDTO> list();
    RetoUsuarioDTO insert(RetoUsuarioDTO dto);
    RetoUsuarioDTO update(RetoUsuarioDTO dto);
    Optional<RetoUsuarioDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: retos aceptados por un usuario
    List<RetoUsuarioDTO> listByUsuarioId(UUID usuarioId);

    // Query de decisión: retos completados (o no) de un usuario
    List<RetoUsuarioDTO> listByUsuarioIdAndCompletado(UUID usuarioId, Boolean completado);
}

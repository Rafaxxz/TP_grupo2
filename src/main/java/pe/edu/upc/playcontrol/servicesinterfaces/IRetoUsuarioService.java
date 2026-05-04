package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;

import java.time.OffsetDateTime;
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

    // Query 1: Dashboard del usuario - progreso consolidado por estado con puntos
    Object dashboardProgresoUsuario(UUID usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);

    // Query 2: Timeline de retos completados en un rango de fechas
    List<RetoUsuarioDTO> listCompletadosByFechaBetween(UUID usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
}

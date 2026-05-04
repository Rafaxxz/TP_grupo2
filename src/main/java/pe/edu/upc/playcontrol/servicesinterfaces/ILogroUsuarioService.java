package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILogroUsuarioService {
    List<LogroUsuarioDTO> list();
    LogroUsuarioDTO insert(LogroUsuarioDTO dto);
    LogroUsuarioDTO update(LogroUsuarioDTO dto);
    Optional<LogroUsuarioDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: logros desbloqueados por un usuario
    List<LogroUsuarioDTO> listByUsuarioId(UUID usuarioId);

    // Query de decisión: cuántos logros ha desbloqueado un usuario
    long contarLogrosPorUsuario(UUID usuarioId);

    // Query 1: Dashboard de progreso - consolidado de logros
    Object dashboardProgresoLogros(UUID usuarioId);

    // Query 2: Timeline de logros desbloqueados en un rango de fechas
    Object findTimelineDesbloqueos(UUID usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
}

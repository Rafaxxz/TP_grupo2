package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ILogroUsuarioService {
    List<LogroUsuarioDTO> list();
    LogroUsuarioDTO insert(LogroUsuarioDTO dto);
    LogroUsuarioDTO update(LogroUsuarioDTO dto);
    Optional<LogroUsuarioDTO> listId(Integer id);
    void delete(Integer id);

    // Filtro simple: logros desbloqueados por un usuario
    List<LogroUsuarioDTO> listByUsuarioId(Integer usuarioId);

    // Query de decisión: cuántos logros ha desbloqueado un usuario
    long contarLogrosPorUsuario(Integer usuarioId);

    // Query 1: Dashboard de progreso - consolidado de logros
    Object dashboardProgresoLogros(Integer usuarioId);

    // Query 2: Timeline de logros desbloqueados en un rango de fechas
    Object findTimelineDesbloqueos(Integer usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
}

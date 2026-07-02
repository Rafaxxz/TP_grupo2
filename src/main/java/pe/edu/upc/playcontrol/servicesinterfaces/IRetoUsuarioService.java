package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IRetoUsuarioService {
    List<RetoUsuarioDTO> list();
    RetoUsuarioDTO insert(RetoUsuarioDTO dto);
    RetoUsuarioDTO update(RetoUsuarioDTO dto);
    Optional<RetoUsuarioDTO> listId(Integer id);
    void delete(Integer id);

    // Filtro simple: retos aceptados por un usuario
    List<RetoUsuarioDTO> listByUsuarioId(Integer usuarioId);

    // Query de decisión: retos completados (o no) de un usuario
    List<RetoUsuarioDTO> listByUsuarioIdAndCompletado(Integer usuarioId, Boolean completado);

    // Query 1: Dashboard del usuario - progreso consolidado por estado con puntos
    Object dashboardProgresoUsuario(Integer usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);

    // Query 2: Timeline de retos completados en un rango de fechas
    List<RetoUsuarioDTO> listCompletadosByFechaBetween(Integer usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
}

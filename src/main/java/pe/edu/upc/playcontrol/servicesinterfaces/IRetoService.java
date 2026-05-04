package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RetoDTO;

import java.util.List;
import java.util.Optional;

public interface IRetoService {
    List<RetoDTO> list();
    RetoDTO insert(RetoDTO dto);
    RetoDTO update(RetoDTO dto);
    Optional<RetoDTO> listId(Integer id);
    void delete(Integer id);

    // Filtro simple: retos filtrados por tipo
    List<RetoDTO> listByTipo(String tipo);

    // Filtro simple: retos activos o inactivos
    List<RetoDTO> listByActivo(Boolean activo);

    // Query 1: Retos disponibles activos filtrados por tipo ordenados por fecha
    List<RetoDTO> listActivosByTipoOrdenado(String tipo);

    // Query 2: Retos próximos a vencer en los próximos 7 días
    List<RetoDTO> listProximosAVencer();
}

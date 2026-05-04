package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.RecompensaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecompensaService {
    List<RecompensaDTO> list();
    RecompensaDTO insert(RecompensaDTO dto);
    RecompensaDTO update(RecompensaDTO dto);
    Optional<RecompensaDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: recompensas filtradas por tipo
    List<RecompensaDTO> listByTipo(String tipo);

    // Query 1: Recompensas disponibles para un usuario según puntos que tiene
    List<RecompensaDTO> findDisponiblesPorPuntos(Integer puntosDisponibles);

    // Query 2: Estadísticas de recompensas por tipo (cantidad, costo mín/máx/promedio)
    Object findEstadisticasPorTipo();
}

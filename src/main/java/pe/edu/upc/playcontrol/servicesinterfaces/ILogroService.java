package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILogroService {
    List<LogroDTO> list();
    LogroDTO insert(LogroDTO dto);
    LogroDTO update(LogroDTO dto);
    Optional<LogroDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: logros según su criterio de desbloqueo
    List<LogroDTO> listByCriterio(String criterio);

    // Query 1: Logros disponibles filtrados por criterio con ordenamiento por dificultad
    List<LogroDTO> listByCriterioOrdenado(String criterio);

    // Query 2: Estadísticas de logros con conteo de desbloqueos
    Object findConEstadisticasDesbloqueos();
}

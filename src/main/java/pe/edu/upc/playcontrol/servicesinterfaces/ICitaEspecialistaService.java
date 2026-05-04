package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICitaEspecialistaService {
    List<CitaEspecialistaDTO> list();
    CitaEspecialistaDTO insert(CitaEspecialistaDTO dto);
    CitaEspecialistaDTO update(CitaEspecialistaDTO dto);
    Optional<CitaEspecialistaDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: citas de un usuario filtradas por estado
    List<CitaEspecialistaDTO> listByUsuarioIdAndEstado(UUID usuarioId, String estado);

    // Query de decisión: todas las citas de un usuario (para ver su historial)
    List<CitaEspecialistaDTO> listByUsuarioId(UUID usuarioId);

    // Query 1: Citas pendientes/confirmadas en los próximos 30 días
    List<CitaEspecialistaDTO> findProximasCitasPendientes(UUID usuarioId);

    // Query 2: Historial de citas por especialista con conteos
    Object historialCitasPorEspecialista(UUID usuarioId);
}

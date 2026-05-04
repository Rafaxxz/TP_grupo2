package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICanjeRecompensaService {
    List<CanjeRecompensaDTO> list();
    CanjeRecompensaDTO insert(CanjeRecompensaDTO dto);
    CanjeRecompensaDTO update(CanjeRecompensaDTO dto);
    Optional<CanjeRecompensaDTO> listId(UUID id);
    void delete(UUID id);

    // Filtro simple: canjes realizados por un usuario
    List<CanjeRecompensaDTO> listByUsuarioId(UUID usuarioId);

    // Query de decisión: total de puntos gastados por un usuario en canjes
    int totalPuntosGastadosPorUsuario(UUID usuarioId);

    // Query 1: BALANCE ACTUAL - puntos ganados vs gastados
    Object balanceActualPuntos(UUID usuarioId);

    // Query 2: Historial de canjes - recompensas reclamadas vs disponibles
    Object historialCanjesVsDisponibles(UUID usuarioId);
}

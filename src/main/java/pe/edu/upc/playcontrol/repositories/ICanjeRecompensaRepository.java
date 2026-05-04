package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla canje_recompensa para filtros y queries de decisión
@Repository
public interface ICanjeRecompensaRepository extends JpaRepository<CanjeRecompensa, UUID> {

    // Filtro simple: trae todos los canjes realizados por un usuario
    List<CanjeRecompensa> findByUsuarioId(UUID usuarioId);

    // Query de decisión: total de puntos gastados por un usuario en canjes
    @Query("SELECT COALESCE(SUM(cr.puntosUsados), 0) FROM CanjeRecompensa cr WHERE cr.usuarioId = :usuarioId")
    int totalPuntosGastadosPorUsuario(@Param("usuarioId") UUID usuarioId);

    // Query 1: BALANCE ACTUAL - puntos ganados vs gastados
    @Query("SELECT " +
           "COALESCE((SELECT SUM(l.puntosOtorgados) FROM LogroUsuario lu JOIN lu.logro l " +
           "WHERE lu.usuarioId = :usuarioId), 0) as puntosGanados, " +
           "COALESCE(SUM(cr.puntosUsados), 0) as puntosGastados, " +
           "COALESCE((SELECT SUM(l.puntosOtorgados) FROM LogroUsuario lu JOIN lu.logro l " +
           "WHERE lu.usuarioId = :usuarioId), 0) - COALESCE(SUM(cr.puntosUsados), 0) as balanceActual " +
           "FROM CanjeRecompensa cr WHERE cr.usuarioId = :usuarioId")
    Object balanceActualPuntos(@Param("usuarioId") UUID usuarioId);

    // Query 2: HISTORIAL DE CANJES - Recompensas reclamadas vs disponibles
    @Query("SELECT 'Reclamadas' as estado, COUNT(cr) as cantidad, " +
           "COALESCE(SUM(r.costoPuntos), 0) as puntosInvertidos " +
           "FROM CanjeRecompensa cr JOIN cr.recompensa r " +
           "WHERE cr.usuarioId = :usuarioId AND cr.canjeadoEn IS NOT NULL " +
           "UNION ALL " +
           "SELECT 'Disponibles' as estado, COUNT(r) as cantidad, " +
           "COALESCE(SUM(r.costoPuntos), 0) as puntosRequeridos " +
           "FROM Recompensa r " +
           "WHERE r.costoPuntos <= " +
           "(COALESCE((SELECT SUM(l.puntosOtorgados) FROM LogroUsuario lu JOIN lu.logro l " +
           "WHERE lu.usuarioId = :usuarioId), 0) - " +
           "COALESCE((SELECT SUM(cr2.puntosUsados) FROM CanjeRecompensa cr2 " +
           "WHERE cr2.usuarioId = :usuarioId), 0))")
    List<Object[]> historialCanjesVsDisponibles(@Param("usuarioId") UUID usuarioId);
}

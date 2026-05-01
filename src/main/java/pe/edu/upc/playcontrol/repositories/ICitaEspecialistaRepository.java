package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla cita_especialista para filtros y queries de decisión
public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, UUID> {

    // Filtro simple: trae citas de un usuario filtradas por estado
    List<CitaEspecialista> findByUsuario_IdUsuarioAndEstado(UUID usuarioId, String estado);

    // Filtro simple: trae todas las citas de un usuario
    List<CitaEspecialista> findByUsuario_IdUsuario(UUID usuarioId);

    // Query 1: Citas pendientes/confirmadas en los próximos 30 días
    @Query("SELECT ce FROM CitaEspecialista ce WHERE ce.usuario.idUsuario = :usuarioId " +
           "AND ce.estado IN ('pendiente', 'confirmada') " +
           "AND ce.fechaHora BETWEEN CURRENT_TIMESTAMP AND CURRENT_TIMESTAMP + 30 " +
           "ORDER BY ce.fechaHora ASC")
    List<CitaEspecialista> findProximasCitasPendientes(@Param("usuarioId") UUID usuarioId);

    // Query 2: Historial completo por especialista (cuántas citas con cada uno y conteo de completadas)
    @Query("SELECT ce.especialista.idEspecialista, ce.especialista.nombre, COUNT(ce) as totalCitas, " +
           "COUNT(CASE WHEN ce.estado = 'completada' THEN 1 END) as completadas " +
           "FROM CitaEspecialista ce WHERE ce.usuario.idUsuario = :usuarioId " +
           "GROUP BY ce.especialista.idEspecialista, ce.especialista.nombre " +
           "ORDER BY totalCitas DESC")
    List<Object[]> historialCitasPorEspecialista(@Param("usuarioId") UUID usuarioId);
}

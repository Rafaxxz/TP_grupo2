package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.RetoUsuario;

import java.time.OffsetDateTime;
import java.util.List;

// Aquí se definen las consultas a la tabla reto_usuario para filtros y queries de decisión
public interface IRetoUsuarioRepository extends JpaRepository<RetoUsuario, Integer> {

    // Filtro simple: trae todos los retos aceptados por un usuario
    List<RetoUsuario> findByUsuario_IdUsuario(Integer usuarioId);

    // Filtro simple: trae retos completados o en progreso de un usuario
    List<RetoUsuario> findByUsuario_IdUsuarioAndCompletado(Integer usuarioId, Boolean completado);

    // Query 1: Dashboard del usuario - progreso consolidado por estado con puntos totales
    @Query("SELECT ru.completado, COUNT(ru) as total, COALESCE(SUM(r.recompensa.costoPuntos), 0) as puntosTotal " +
           "FROM RetoUsuario ru JOIN ru.reto r WHERE ru.usuario.idUsuario = :usuarioId " +
           "AND ru.aceptadoEn BETWEEN :fechaInicio AND :fechaFin GROUP BY ru.completado")
    List<Object[]> dashboardProgresoUsuario(@Param("usuarioId") Integer usuarioId,
                                             @Param("fechaInicio") OffsetDateTime fechaInicio,
                                             @Param("fechaFin") OffsetDateTime fechaFin);

    // Query 2: Retos completados en un rango de fechas (timeline de progreso)
    @Query("SELECT ru FROM RetoUsuario ru WHERE ru.usuario.idUsuario = :usuarioId " +
           "AND ru.completado = true AND ru.finalizadoEn BETWEEN :fechaInicio AND :fechaFin " +
           "ORDER BY ru.finalizadoEn DESC")
    List<RetoUsuario> findCompletadosByFechaBetween(@Param("usuarioId") Integer usuarioId,
                                                     @Param("fechaInicio") OffsetDateTime fechaInicio,
                                                     @Param("fechaFin") OffsetDateTime fechaFin);
}

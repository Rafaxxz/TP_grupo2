package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.LogroUsuario;

import java.time.OffsetDateTime;
import java.util.List;

// Aquí se definen las consultas a la tabla logro_usuario para filtros y queries de decisión
@Repository
public interface ILogroUsuarioRepository extends JpaRepository<LogroUsuario, Integer> {

    // Filtro simple: trae todos los logros desbloqueados de un usuario
    List<LogroUsuario> findByUsuarioId(Integer usuarioId);

    // Query de decisión: cuántos logros ha desbloqueado un usuario
    @Query("SELECT COUNT(lu) FROM LogroUsuario lu WHERE lu.usuarioId = :usuarioId")
    long contarLogrosPorUsuario(@Param("usuarioId") Integer usuarioId);

    // Query 1: Dashboard de progreso - consolidado de logros desbloqueados con puntos
    @Query("SELECT COUNT(lu) as totalDesbloqueos, COALESCE(SUM(l.puntosOtorgados), 0) as puntosTotales " +
           "FROM LogroUsuario lu JOIN lu.logro l WHERE lu.usuarioId = :usuarioId")
    Object dashboardProgresoLogros(@Param("usuarioId") Integer usuarioId);

    // Query 2: Timeline de logros desbloqueados en un rango de fechas
    @Query("SELECT lu, l.nombre, lu.desbloqueadoEn, l.puntosOtorgados " +
           "FROM LogroUsuario lu JOIN lu.logro l " +
           "WHERE lu.usuarioId = :usuarioId AND lu.desbloqueadoEn BETWEEN :fechaInicio AND :fechaFin " +
           "ORDER BY lu.desbloqueadoEn DESC")
    List<Object[]> findTimelineDesbloqueos(@Param("usuarioId") Integer usuarioId,
                                            @Param("fechaInicio") OffsetDateTime fechaInicio,
                                            @Param("fechaFin") OffsetDateTime fechaFin);
}

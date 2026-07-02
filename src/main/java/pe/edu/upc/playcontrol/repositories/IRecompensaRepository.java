package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.Recompensa;

import java.util.List;

// Aquí se definen las consultas a la tabla recompensa para filtros y queries de decisión
@Repository
public interface IRecompensaRepository extends JpaRepository<Recompensa, Integer> {

    // Filtro simple: trae recompensas filtradas por tipo
    List<Recompensa> findByTipo(String tipo);

    // Query 1: Recompensas disponibles para un usuario (filtradas por puntos que tiene)
    @Query("SELECT r FROM Recompensa r " +
           "WHERE r.costoPuntos <= :puntosDisponibles " +
           "ORDER BY r.costoPuntos ASC")
    List<Recompensa> findDisponiblesPorPuntos(@Param("puntosDisponibles") Integer puntosDisponibles);

    // Query 2: Estadísticas de recompensas por tipo (cantidad, costo mín/máx/promedio)
    @Query("SELECT r.tipo, COUNT(r) as cantidad, MIN(r.costoPuntos) as costoMinimo, " +
           "MAX(r.costoPuntos) as costoMaximo, AVG(r.costoPuntos) as costoPromedio " +
           "FROM Recompensa r GROUP BY r.tipo ORDER BY cantidad DESC")
    List<Object[]> findEstadisticasPorTipo();
}

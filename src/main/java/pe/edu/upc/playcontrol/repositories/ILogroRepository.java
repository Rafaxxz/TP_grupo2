package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.Logro;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla logro para filtros y queries de decisión
@Repository
public interface ILogroRepository extends JpaRepository<Logro, UUID> {

    // Filtro simple: trae logros según su criterio de desbloqueo
    List<Logro> findByCriterio(String criterio);

    // Query 1: Logros disponibles filtrados por criterio con ordenamiento por puntos (menor a mayor)
    @Query("SELECT l FROM Logro l WHERE l.criterio = :criterio " +
           "ORDER BY l.puntosOtorgados ASC")
    List<Logro> findByCriterioOrdenado(@Param("criterio") String criterio);

    // Query 2: Estadísticas de logros con conteo de desbloqueos
    @Query("SELECT l.idLogro, l.nombre, l.criterio, COUNT(lu) as usuariosQueLoDesbloquearon " +
           "FROM Logro l LEFT JOIN LogroUsuario lu ON l.idLogro = lu.logro.idLogro " +
           "GROUP BY l.idLogro, l.nombre, l.criterio ORDER BY usuariosQueLoDesbloquearon DESC")
    List<Object[]> findConEstadisticasDesbloqueos();
}

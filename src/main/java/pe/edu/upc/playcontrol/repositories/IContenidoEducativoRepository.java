package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.ContenidoEducativo;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla contenido_educativo para filtros y queries de decisión
public interface IContenidoEducativoRepository extends JpaRepository<ContenidoEducativo, UUID> {

    // Filtro simple: trae contenidos filtrados por tipo (articulo, video, guia, podcast)
    List<ContenidoEducativo> findByTipo(String tipo);

    // Query 1: Contenidos por tipo y ordenados por fecha de publicación (más recientes primero)
    @Query("SELECT ce FROM ContenidoEducativo ce WHERE ce.tipo = :tipo " +
           "ORDER BY ce.publicadoEn DESC")
    List<ContenidoEducativo> findByTipoOrdenadoPorFecha(@Param("tipo") String tipo);

    // Query 2: Contenidos más recientes del tipo especificado (últimos contenidos publicados)
    @Query("SELECT ce FROM ContenidoEducativo ce WHERE ce.tipo = :tipo " +
           "ORDER BY ce.publicadoEn DESC LIMIT 10")
    List<ContenidoEducativo> findUltimosContenidosPorTipo(@Param("tipo") String tipo);
}

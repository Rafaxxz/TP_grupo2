package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Reto;

import java.util.List;

// Aquí se definen las consultas a la tabla reto para filtros y queries de decisión
public interface IRetoRepository extends JpaRepository<Reto, Integer> {

    // Filtro simple: trae retos según su tipo (ej: "semanal", "familiar")
    List<Reto> findByTipo(String tipo);

    // Filtro simple: trae retos activos o inactivos
    List<Reto> findByActivo(Boolean activo);

    // Query 1: Retos disponibles activos filtrados por tipo con ordenamiento por título
    @Query("SELECT r FROM Reto r WHERE r.activo = true AND r.tipo = :tipo ORDER BY r.titulo ASC")
    List<Reto> findActivosByTipoOrdenado(@Param("tipo") String tipo);

    // Query 2: Retos ordenados por dificultad (los más accesibles primero)
    @Query("SELECT r FROM Reto r WHERE r.activo = true ORDER BY r.dificultad ASC, r.duracionDias ASC")
    List<Reto> findProximosAVencer();
}

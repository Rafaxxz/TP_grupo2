package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Reto;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla reto para filtros y CRUD
public interface IRetoRepository extends JpaRepository<Reto, UUID> {

    // Filtro simple: trae retos según su tipo (ej: "semanal", "familiar")
    List<Reto> findByTipo(String tipo);

    // Filtro simple: trae retos activos o inactivos
    List<Reto> findByActivo(Boolean activo);
}

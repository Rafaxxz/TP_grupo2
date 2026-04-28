package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.ContenidoEducativo;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla contenido_educativo para filtros y CRUD
public interface IContenidoEducativoRepository extends JpaRepository<ContenidoEducativo, UUID> {

    // Filtro simple: trae contenidos por tipo (ej: "articulo", "video", "guia")
    List<ContenidoEducativo> findByTipo(String tipo);
}

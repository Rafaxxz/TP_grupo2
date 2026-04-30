package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;

import java.util.List;

public interface CategoriaJuegoRepository extends JpaRepository<CategoriaJuego, Integer> {
    @Query("SELECT c FROM CategoriaJuego c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<CategoriaJuego> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT COUNT(c) > 0 FROM CategoriaJuego c WHERE LOWER(c.nombre) = LOWER(:nombre)")
    boolean existePorNombre(@Param("nombre") String nombre);
}

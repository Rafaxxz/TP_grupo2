package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;

<<<<<<< HEAD:src/main/java/pe/edu/upc/playcontrol/repositories/CategoriaJuegoRepository.java
import java.util.List;

public interface CategoriaJuegoRepository extends JpaRepository<CategoriaJuego, Integer> {
    @Query("SELECT c FROM CategoriaJuego c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<CategoriaJuego> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT COUNT(c) > 0 FROM CategoriaJuego c WHERE LOWER(c.nombre) = LOWER(:nombre)")
    boolean existePorNombre(@Param("nombre") String nombre);
}
=======
public interface ICategoriaJuegoRepository extends JpaRepository<CategoriaJuego, Integer> {
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2:src/main/java/pe/edu/upc/playcontrol/repositories/ICategoriaJuegoRepository.java

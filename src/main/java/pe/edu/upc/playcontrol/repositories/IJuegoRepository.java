package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;

public interface IJuegoRepository extends JpaRepository<Juego, Integer> {
    List<Juego> findByPlataformaIgnoreCase(String plataforma);
    List<Juego> findByCategoriaJuego_IdCategoria(Integer idCategoria);

    @Query(value = """
      SELECT c.nombre, COUNT(j.id_juego)
      FROM categoria_juego c LEFT JOIN juego j ON j.categoria_id = c.id_categoria
      GROUP BY c.nombre ORDER BY c.nombre
      """, nativeQuery = true)
    List<Object[]> contarJuegosPorCategoria();
}

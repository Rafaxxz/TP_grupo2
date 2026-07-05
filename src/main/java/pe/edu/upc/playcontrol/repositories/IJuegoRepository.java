package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;
import java.util.Optional;

public interface IJuegoRepository extends JpaRepository<Juego, Integer> {
    List<Juego> findByPlataformaIgnoreCase(String plataforma);
    List<Juego> findByCategoriaJuego_IdCategoria(Integer idCategoria);
    Optional<Juego> findByNombreIgnoreCase(String nombre);

    @Query(value = """
      SELECT c.nombre, COUNT(j.id_juego)
      FROM categoria_juego c LEFT JOIN juego j ON j.categoria_id = c.id_categoria
      GROUP BY c.nombre ORDER BY c.nombre
      """, nativeQuery = true)
    List<Object[]> contarJuegosPorCategoria();

    // Consulta cruzada: juegos filtrados por nombre de categoria (juego JOIN categoria_juego)
    @Query(value = """
      SELECT j.nombre, COALESCE(j.plataforma, '-'), c.nombre
      FROM juego j INNER JOIN categoria_juego c ON j.categoria_id = c.id_categoria
      WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :categoria, '%'))
      ORDER BY j.nombre
      """, nativeQuery = true)
    List<Object[]> buscarJuegosPorCategoria(@Param("categoria") String categoria);
}

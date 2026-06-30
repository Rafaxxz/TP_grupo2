package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPadreId(Integer padreId);

    @Query("SELECT u FROM Usuario u WHERE u.createdAt >= :fecha")
    List<Usuario> findLastUsers(@Param("fecha") OffsetDateTime fecha);

    @Query("SELECT u FROM Usuario u, Rol r WHERE u.idRol = r.idRol AND r.nombre = :nombre")
    List<Usuario> findByRolNombre(@Param("nombre") String nombre);

    @Query(value = """
      SELECT MONTH(u.created_at) AS mes, COUNT(*) AS total FROM usuario u
      WHERE YEAR(u.created_at) = YEAR(CURRENT_DATE) GROUP BY MONTH(u.created_at)
      ORDER BY mes
      """, nativeQuery = true)
    List<Object[]> usuariosRegistradosPorMes();

    @Query(value = """
      SELECT u.nombre AS nombre, SUM(s.duracion_minutos) AS total_minutos
      FROM sesion_juego s
      JOIN usuario u ON s.usuario_id = u.id_usuario
      WHERE MONTH(s.fecha) = MONTH(CURRENT_DATE)
      AND YEAR(s.fecha) = YEAR(CURRENT_DATE)
      GROUP BY u.nombre
      """, nativeQuery = true)
    List<Object[]> tiempoJugadoPorHijoMesActual();
}

package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.time.LocalDate;
import java.util.List;

public interface SesionJuegoRepository extends JpaRepository<SesionJuego, Integer> {
    List<SesionJuego> findByUsuario_IdUsuario(Integer usuarioId);
    List<SesionJuego> findByFecha(LocalDate fecha);

    // Querys Rafael
    List<SesionJuego> findByJuego_IdJuego(Integer juegoId);
    List<SesionJuego> findByUsuario_IdUsuarioAndJuego_IdJuego(Integer usuarioId, Integer juegoId);

    @Query("SELECT COALESCE(SUM(s.duracionMinutos), 0) FROM SesionJuego s WHERE s.usuario.idUsuario = :uid AND s.fecha = :fecha")
    Integer sumMinutosByUsuarioAndFecha(@Param("uid") Integer uid, @Param("fecha") LocalDate fecha);

    @Query(value = """
      SELECT u.username, COALESCE(SUM(s.duracion_minutos), 0)
      FROM usuario u INNER JOIN sesion_juego s ON s.usuario_id = u.id_usuario
      GROUP BY u.username ORDER BY 2 DESC
      """, nativeQuery = true)
    List<Object[]> sumarMinutosPorUsuario();
}

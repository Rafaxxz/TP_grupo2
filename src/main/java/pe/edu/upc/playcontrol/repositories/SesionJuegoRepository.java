package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.util.List;

public interface SesionJuegoRepository extends JpaRepository<SesionJuego, Integer> {

    @Query(value = "SELECT * FROM sesion_juego WHERE usuario_id = :usuarioId ORDER BY fecha DESC, inicio DESC", nativeQuery = true)
    List<SesionJuego> historialPorUsuario(@Param("usuarioId") Integer usuarioId);
}
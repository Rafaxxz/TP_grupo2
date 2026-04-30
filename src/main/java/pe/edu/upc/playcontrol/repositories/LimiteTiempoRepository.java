package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LimiteTiempoRepository extends JpaRepository<LimiteTiempo, Integer> {
    @Query("SELECT s FROM SesionJuego s WHERE s.usuario.idUsuario = :usuarioId")
    List<SesionJuego> buscarPorUsuario(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT s FROM SesionJuego s WHERE s.fecha = :fecha")
    List<SesionJuego> buscarPorFecha(@Param("fecha") LocalDate fecha);
}





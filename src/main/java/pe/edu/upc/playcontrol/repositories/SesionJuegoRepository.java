package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.time.LocalDate;
import java.util.List;

public interface SesionJuegoRepository extends JpaRepository<SesionJuego, Integer> {
    List<SesionJuego> findByUsuario_IdUsuario(Integer usuarioId);
    List<SesionJuego> findByFecha(LocalDate fecha);
}

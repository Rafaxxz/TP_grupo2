package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.SesionJuego;

import java.util.UUID;

public interface SesionJuegoRepository extends JpaRepository<SesionJuego, UUID> {
}
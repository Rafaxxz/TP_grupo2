package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Juego;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {
}
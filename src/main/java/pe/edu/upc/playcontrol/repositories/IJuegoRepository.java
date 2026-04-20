package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.UUID;

public interface IJuegoRepository extends JpaRepository<Juego, UUID> {
}
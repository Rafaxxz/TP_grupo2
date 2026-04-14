package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;

public interface CategoriaJuegoRepository extends JpaRepository<CategoriaJuego, Integer> {
}

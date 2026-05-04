package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;

public interface IJuegoRepository extends JpaRepository<Juego, Integer> {
    List<Juego> findByPlataformaIgnoreCase(String plataforma);
    List<Juego> findByCategoriaJuego_IdCategoria(Integer idCategoria);
}

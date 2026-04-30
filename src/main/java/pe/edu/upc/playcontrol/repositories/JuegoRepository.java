package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Juego;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {
    @Query("SELECT j FROM Juego j WHERE LOWER(j.plataforma) = LOWER(:plataforma)")
    List<Juego> buscarPorPlataforma(@Param("plataforma") String plataforma);

    @Query("SELECT j FROM Juego j WHERE j.categoriaJuego.idCategoria = :idCategoria")
    List<Juego> buscarPorCategoria(@Param("idCategoria") Integer idCategoria);
}
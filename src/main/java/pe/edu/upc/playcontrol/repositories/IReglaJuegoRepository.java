package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.ReglaJuego;

import java.util.List;

public interface IReglaJuegoRepository extends JpaRepository<ReglaJuego, Integer> {
    List<ReglaJuego> findByHijo_IdUsuario(Integer hijoId);
}

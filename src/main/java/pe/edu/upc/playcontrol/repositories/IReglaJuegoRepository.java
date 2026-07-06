package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.ReglaJuego;

import java.util.List;
import java.util.Optional;

public interface IReglaJuegoRepository extends JpaRepository<ReglaJuego, Integer> {
    List<ReglaJuego> findByHijo_IdUsuario(Integer hijoId);
    Optional<ReglaJuego> findByHijo_IdUsuarioAndJuego_IdJuego(Integer hijoId, Integer juegoId);
}

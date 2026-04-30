package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;

import java.util.List;

@Repository
public interface ICanjeRecompensaRepository extends JpaRepository<CanjeRecompensa, Integer> {

    List<CanjeRecompensa> findByUsuarioId(Integer usuarioId);
}

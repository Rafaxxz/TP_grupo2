package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICanjeRecompensaRepository extends JpaRepository<CanjeRecompensa, UUID> {

    List<CanjeRecompensa> findByUsuarioId(UUID usuarioId);
}

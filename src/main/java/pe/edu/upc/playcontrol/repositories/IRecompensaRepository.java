package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.Recompensa;

import java.util.UUID;

@Repository
public interface IRecompensaRepository extends JpaRepository<Recompensa, UUID> {
}

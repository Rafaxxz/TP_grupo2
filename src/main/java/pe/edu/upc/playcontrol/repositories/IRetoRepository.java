package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Reto;

import java.util.UUID;

public interface IRetoRepository extends JpaRepository<Reto, UUID> {
}

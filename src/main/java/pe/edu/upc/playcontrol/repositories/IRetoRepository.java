package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Reto;

public interface IRetoRepository extends JpaRepository<Reto, Integer> {
}

package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Mensaje;

public interface IMensajeRepository extends JpaRepository<Mensaje, Integer> {
}

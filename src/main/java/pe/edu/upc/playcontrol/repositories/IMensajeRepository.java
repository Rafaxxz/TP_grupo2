package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Mensaje;

import java.util.UUID;

public interface IMensajeRepository extends JpaRepository<Mensaje, UUID> {
}

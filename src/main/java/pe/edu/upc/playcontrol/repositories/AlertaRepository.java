package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Alerta;

import java.util.UUID;

public interface AlertaRepository extends JpaRepository<Alerta, UUID> {
}
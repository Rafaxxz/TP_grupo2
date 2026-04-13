package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;

import java.util.UUID;

public interface LimiteTiempoRepository extends JpaRepository<LimiteTiempo, UUID> {
}
package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.ContenidoEducativo;

import java.util.UUID;

public interface IContenidoEducativoRepository extends JpaRepository<ContenidoEducativo, UUID> {
}

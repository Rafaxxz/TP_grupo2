package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.RetoUsuario;

import java.util.UUID;

public interface IRetoUsuarioRepository extends JpaRepository<RetoUsuario, UUID> {
}

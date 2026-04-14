package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.util.UUID;

public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
}

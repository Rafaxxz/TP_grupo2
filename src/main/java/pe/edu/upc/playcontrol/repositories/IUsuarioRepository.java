package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}

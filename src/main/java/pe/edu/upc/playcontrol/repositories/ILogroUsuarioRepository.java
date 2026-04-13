package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.LogroUsuario;

import java.util.List;
import java.util.UUID;

@Repository
public interface ILogroUsuarioRepository extends JpaRepository<LogroUsuario, UUID> {

    List<LogroUsuario> findByUsuarioId(UUID usuarioId);
}

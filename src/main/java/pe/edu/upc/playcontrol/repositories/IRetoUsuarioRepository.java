package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.RetoUsuario;

public interface IRetoUsuarioRepository extends JpaRepository<RetoUsuario, Integer> {
}

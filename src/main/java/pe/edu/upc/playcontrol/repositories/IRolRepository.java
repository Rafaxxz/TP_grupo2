package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Rol;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
}

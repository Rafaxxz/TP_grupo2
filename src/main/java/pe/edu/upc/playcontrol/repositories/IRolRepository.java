package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Rol;

import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}

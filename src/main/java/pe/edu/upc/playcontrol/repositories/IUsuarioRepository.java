package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Usuario;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.createdAt >= :fecha")
    List<Usuario> findLastUsers(@Param("fecha") OffsetDateTime fecha);

    @Query("SELECT u FROM Usuario u, Rol r WHERE u.idRol = r.idRol AND r.nombre = :nombre")
    List<Usuario> findByRolNombre(@Param("nombre") String nombre);
}

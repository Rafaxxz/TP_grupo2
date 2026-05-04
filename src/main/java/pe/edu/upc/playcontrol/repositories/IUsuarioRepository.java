package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Usuario;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Query("select u from Usuario u where u.createdAt >= :fecha")
    List<Usuario> findLastUsers(@Param("fecha") OffsetDateTime fecha);

    @Query("select lr from Usuario lr join lr.rol r where r.nombre = :nombre")
    List<Usuario> findByRolNombre(@Param("nombre") String nombre);
}

package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.RetoUsuario;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla reto_usuario para filtros y decisión
public interface IRetoUsuarioRepository extends JpaRepository<RetoUsuario, UUID> {

    // Filtro simple: trae todos los retos de un usuario
    List<RetoUsuario> findByUsuario_IdUsuario(UUID usuarioId);

    // Query de decisión: trae solo los retos completados de un usuario
    List<RetoUsuario> findByUsuario_IdUsuarioAndCompletado(UUID usuarioId, Boolean completado);
}

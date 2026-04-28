package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla cita_especialista para filtros y decisión
public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, UUID> {

    // Filtro simple: trae citas de un usuario por su estado
    List<CitaEspecialista> findByUsuario_IdUsuarioAndEstado(UUID usuarioId, String estado);

    // Query de decisión: trae todas las citas pendientes de un usuario
    List<CitaEspecialista> findByUsuario_IdUsuario(UUID usuarioId);
}

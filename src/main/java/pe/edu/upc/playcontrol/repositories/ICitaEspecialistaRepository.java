package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.List;

public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, Integer> {
    List<CitaEspecialista> findByUsuario_IdUsuario(Integer usuarioId);

    @Query(value = """
      SELECT estado, COUNT(*) FROM cita_especialista GROUP BY estado ORDER BY 2 DESC
      """, nativeQuery = true)
    List<Object[]> contarCitasPorEstado();

    // Consulta cruzada: citas filtradas por estado (cita JOIN usuario JOIN especialista)
    @Query(value = """
      SELECT u.nombre, e.especialidad, ci.fecha_hora, ci.estado
      FROM cita_especialista ci
      INNER JOIN usuario u ON ci.usuario_id = u.id_usuario
      INNER JOIN especialista e ON ci.especialista_id = e.id_especialista
      WHERE LOWER(ci.estado) LIKE LOWER(CONCAT('%', :estado, '%'))
      ORDER BY ci.fecha_hora DESC
      """, nativeQuery = true)
    List<Object[]> buscarCitasPorEstado(@Param("estado") String estado);
}

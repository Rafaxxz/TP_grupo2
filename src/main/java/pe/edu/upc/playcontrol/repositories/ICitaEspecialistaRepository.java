package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.List;

public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, Integer> {
    List<CitaEspecialista> findByUsuario_IdUsuario(Integer usuarioId);

    @Query(value = """
      SELECT estado, COUNT(*) FROM cita_especialista GROUP BY estado ORDER BY 2 DESC
      """, nativeQuery = true)
    List<Object[]> contarCitasPorEstado();
}

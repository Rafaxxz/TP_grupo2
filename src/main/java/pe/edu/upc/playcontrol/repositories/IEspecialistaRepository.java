package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.playcontrol.entities.Especialista;

import java.util.List;

public interface IEspecialistaRepository extends JpaRepository<Especialista, Integer> {
    List<Especialista> findByVerificadoTrue();

    @Query(value = """
      SELECT COALESCE(modalidad, 'Sin definir'), COUNT(*)
      FROM especialista GROUP BY modalidad ORDER BY 2 DESC
      """, nativeQuery = true)
    List<Object[]> contarEspecialistasPorModalidad();
}

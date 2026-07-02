package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Especialista;

import java.util.List;

public interface IEspecialistaRepository extends JpaRepository<Especialista, Integer> {
    List<Especialista> findByVerificadoTrue();

    @Query(value = """
      SELECT COALESCE(modalidad, 'Sin definir'), COUNT(*)
      FROM especialista GROUP BY modalidad ORDER BY 2 DESC
      """, nativeQuery = true)
    List<Object[]> contarEspecialistasPorModalidad();

    // Consulta cruzada: especialistas filtrados por nombre del usuario (especialista JOIN usuario)
    @Query(value = """
      SELECT u.nombre, e.especialidad, COALESCE(e.modalidad, 'Sin definir')
      FROM especialista e INNER JOIN usuario u ON e.usuario_id = u.id_usuario
      WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
      ORDER BY u.nombre
      """, nativeQuery = true)
    List<Object[]> buscarEspecialistasPorNombre(@Param("texto") String texto);
}

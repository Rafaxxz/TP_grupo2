package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;

import java.util.List;

public interface LimiteTiempoRepository extends JpaRepository<LimiteTiempo, Integer> {
    List<LimiteTiempo> findByUsuario_IdUsuario(Integer usuarioId);
    List<LimiteTiempo> findByBloqueoActivoTrue();
}

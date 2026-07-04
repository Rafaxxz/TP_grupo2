package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;

import java.util.List;
import java.util.Optional;

public interface LimiteTiempoRepository extends JpaRepository<LimiteTiempo, Integer> {
    List<LimiteTiempo> findByUsuario_IdUsuario(Integer usuarioId);
    List<LimiteTiempo> findByBloqueoActivoTrue();

    // Para WebSocket: obtener el límite activo de un hijo (el más reciente)
    Optional<LimiteTiempo> findFirstByUsuario_IdUsuarioAndBloqueoActivoTrueOrderByActualizadoEnDesc(Integer usuarioId);
}

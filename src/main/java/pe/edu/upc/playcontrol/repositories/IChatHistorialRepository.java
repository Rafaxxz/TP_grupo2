package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.ChatHistorial;

import java.util.List;

public interface IChatHistorialRepository extends JpaRepository<ChatHistorial, Integer> {

    // Historial completo del usuario, del más reciente al más antiguo
    List<ChatHistorial> findByUsuario_IdUsuarioOrderByCreadoEnDesc(Integer usuarioId);

    // Últimas interacciones para armar el contexto conversacional (multi-turn)
    List<ChatHistorial> findTop6ByUsuario_IdUsuarioOrderByCreadoEnDesc(Integer usuarioId);
}

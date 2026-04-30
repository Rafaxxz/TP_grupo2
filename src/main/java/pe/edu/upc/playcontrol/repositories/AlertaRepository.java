package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Alerta;

import java.util.List;
import java.util.UUID;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    @Query("SELECT a FROM Alerta a WHERE a.usuario.idUsuario = :usuarioId")
    List<Alerta> buscarPorUsuario(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT a FROM Alerta a WHERE a.leida = false")
    List<Alerta> obtenerNoLeidas();
}



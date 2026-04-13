package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICanjeRecompensaRepository extends JpaRepository<CanjeRecompensa, UUID> {

    // con JpaRepository se acceden a varios metodos como save, findAll, etc.
    //aqui se hace SELECT * FROM canje_recompensa WHERE usuario_id = ?
    List<CanjeRecompensa> findByUsuarioId(UUID usuarioId);
}

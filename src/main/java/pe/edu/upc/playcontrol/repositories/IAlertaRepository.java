package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Alerta;

import java.util.List;

public interface IAlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByUsuario_IdUsuario(Integer usuarioId);
    List<Alerta> findByLeidaFalse();
}

package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.List;

public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, Integer> {
    List<CitaEspecialista> findByUsuario_IdUsuario(Integer usuarioId);
}

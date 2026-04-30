package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.List;
import java.util.UUID;

public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, UUID> {
    List<CitaEspecialista> findByUsuario_IdUsuario(UUID usuarioId);//poner cantidad de citas realizadas y las que no
}

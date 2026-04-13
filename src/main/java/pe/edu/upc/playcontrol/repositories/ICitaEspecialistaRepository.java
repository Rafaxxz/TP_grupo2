package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;

import java.util.UUID;

public interface ICitaEspecialistaRepository extends JpaRepository<CitaEspecialista, UUID> {
}

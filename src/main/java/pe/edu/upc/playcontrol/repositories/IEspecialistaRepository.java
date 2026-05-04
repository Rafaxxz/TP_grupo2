package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Especialista;

import java.util.List;
import java.util.UUID;

public interface IEspecialistaRepository extends JpaRepository<Especialista, UUID> {
    List<Especialista> findByVerificateTrue();
}

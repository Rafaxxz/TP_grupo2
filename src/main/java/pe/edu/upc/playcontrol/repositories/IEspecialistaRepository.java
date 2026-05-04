package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Especialista;

import java.util.List;

public interface IEspecialistaRepository extends JpaRepository<Especialista, Integer> {
    List<Especialista> findByVerificadoTrue();
}

package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.Logro;

@Repository
public interface ILogroRepository extends JpaRepository<Logro, Integer> {

}

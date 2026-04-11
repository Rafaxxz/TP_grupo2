package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.playcontrol.entities.Logro;

import java.util.UUID;

// @Repository marca esta interfaz como un bean de Spring para acceso a datos.
// JpaRepository<Logro, UUID> te da gratis, sin escribir SQL:
//   findAll(), findById(), save(), deleteById(), count(), existsById(), y más.
// Spring Data JPA genera la implementación en tiempo de ejecución automáticamente.
@Repository
public interface ILogroRepository extends JpaRepository<Logro, UUID> {
    // Si necesitas queries personalizadas, Spring Data las genera por nombre:
    // findByNombre(String nombre) → SELECT * FROM logro WHERE nombre = ?
    // findByCriterio(String criterio) → SELECT * FROM logro WHERE criterio = ?
}

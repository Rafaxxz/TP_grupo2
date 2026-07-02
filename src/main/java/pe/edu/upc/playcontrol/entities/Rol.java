package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rol")
@Getter @Setter @NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre", nullable = false, unique = true, length = 20)
    private String nombre;
}

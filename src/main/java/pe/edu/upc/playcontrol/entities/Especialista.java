package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
=======
import java.util.UUID;

>>>>>>> fabrizzio-salvador
@Entity
@Table(name = "especialista")
@Getter @Setter @NoArgsConstructor
public class Especialista {

    @Id
<<<<<<< HEAD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialista")
    private Integer idEspecialista;
=======
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_especialista", updatable = false, nullable = false)
    private UUID idEspecialista;
>>>>>>> fabrizzio-salvador

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "especialidad", nullable = false, length = 150)
    private String especialidad;

    @Column(name = "modalidad", length = 60)
    private String modalidad;

    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;
}

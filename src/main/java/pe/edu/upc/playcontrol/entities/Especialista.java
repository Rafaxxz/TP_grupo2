package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "especialista")
@Getter @Setter @NoArgsConstructor
public class Especialista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialista")
    private Integer idEspecialista;

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

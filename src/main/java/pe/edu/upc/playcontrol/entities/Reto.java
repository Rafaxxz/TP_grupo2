package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reto")
    private Integer idReto;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(name = "dificultad", length = 10)
    private String dificultad;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "recompensa_id")
    private Recompensa recompensa;

    @ManyToOne
    @JoinColumn(name = "logro_id")
    private Logro logro;
}

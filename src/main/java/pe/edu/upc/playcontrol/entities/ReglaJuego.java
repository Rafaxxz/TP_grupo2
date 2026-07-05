package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Regla de juego que un PADRE asigna a un HIJO: enlace del juego, horario
// permitido, minutos máximos por día y bloqueo manual.
@Entity
@Table(name = "regla_juego")
@Getter @Setter @NoArgsConstructor
public class ReglaJuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regla")
    private Integer idRegla;

    @ManyToOne
    @JoinColumn(name = "hijo_id", nullable = false)
    private Usuario hijo;

    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "hora_inicio", length = 5)
    private String horaInicio;

    @Column(name = "hora_fin", length = 5)
    private String horaFin;

    @Column(name = "minutos_maximos", nullable = false)
    private Integer minutosMaximos;

    @Column(name = "bloqueado", nullable = false)
    private Boolean bloqueado = false;
}

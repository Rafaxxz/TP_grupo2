package pe.edu.upc.playcontrol.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesion_juego")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SesionJuego {

    @Id
    @GeneratedValue
    @Column(name = "id_sesion")
    private UUID idSesion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @Column(name = "inicio", nullable = false)
    private OffsetDateTime inicio;

    @Column(name = "fin")
    private OffsetDateTime fin;

    @Column(name = "duracion_minutos", insertable = false, updatable = false)
    private Integer duracionMinutos;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}
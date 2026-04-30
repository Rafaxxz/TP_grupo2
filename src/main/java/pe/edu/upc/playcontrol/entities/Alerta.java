package pe.edu.upc.playcontrol.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    private SesionJuego sesionJuego;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "nivel", length = 20)
    private String nivel;

    @Column(name = "emitida_en", nullable = false)
    private OffsetDateTime emitidaEn;

    @Column(name = "leida", nullable = false)
    private Boolean leida;
}
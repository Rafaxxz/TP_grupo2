package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "limite_tiempo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "tipo"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimiteTiempo {


    @Id
    @GeneratedValue
    @Column(name = "id_limite")
    private UUID idLimite;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;

    @Column(name = "minutos_maximos", nullable = false)
    private Integer minutosMaximos;

    @Column(name = "bloqueo_activo", nullable = false)
    private Boolean bloqueoActivo;

    @Column(name = "notificar", nullable = false)
    private Boolean notificar;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;
}
package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "canje_recompensa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CanjeRecompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_canje", updatable = false, nullable = false)
    private UUID idCanje;

    // FK a usuario — misma razón que en LogroUsuario: se deja como UUID simple.
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recompensa_id")
    private Recompensa recompensa;

    @Column(name = "puntos_usados", nullable = false)
    private int puntosUsados;

    @Column(name = "canjeado_en", nullable = false)
    private OffsetDateTime canjeadoEn;

    @PrePersist
    public void prePersist() {
        if (canjeadoEn == null) {
            canjeadoEn = OffsetDateTime.now();
        }
    }
}

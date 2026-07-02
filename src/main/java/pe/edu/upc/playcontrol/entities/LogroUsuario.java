package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "logro_usuario",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "logro_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logro_id")
    private Logro logro;

    @Column(name = "desbloqueado_en", nullable = false)
    private OffsetDateTime desbloqueadoEn;

    @PrePersist
    public void prePersist() {
        if (desbloqueadoEn == null) desbloqueadoEn = OffsetDateTime.now();
    }
}

package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reto_usuario")
@Getter @Setter @NoArgsConstructor
public class RetoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reto_id", nullable = false)
    private Reto reto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "aceptado_en", nullable = false)
    private OffsetDateTime aceptadoEn;

    @Column(name = "completado", nullable = false)
    private Boolean completado = false;

    @Column(name = "finalizado_en")
    private OffsetDateTime finalizadoEn;

    @PrePersist
    public void prePersist() {
        if (aceptadoEn == null) aceptadoEn = OffsetDateTime.now();
        if (completado == null) completado = false;
    }
}

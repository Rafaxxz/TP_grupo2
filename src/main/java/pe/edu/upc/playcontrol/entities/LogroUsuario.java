package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "logro_usuario",
    uniqueConstraints = {
        // Refleja el UNIQUE (usuario_id, logro_id) del DDL:
        // un usuario no puede desbloquear el mismo logro dos veces.
        @UniqueConstraint(columnNames = {"usuario_id", "logro_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // usuario_id es FK a la tabla usuario, cuya entidad (@Entity Usuario)
    // es responsabilidad de otro integrante del grupo.
    // Se mapea como UUID simple (sin @ManyToOne) para evitar dependencia
    // de compilación cruzada: este código compila aunque Usuario no exista aún.
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    // logro_id sí se mapea con @ManyToOne porque Logro es nuestra entidad.
    // FetchType.LAZY: Hibernate NO carga el Logro completo hasta que lo uses,
    // lo que evita consultas innecesarias a la BD (mejor rendimiento).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logro_id")
    private Logro logro;

    // TIMESTAMPTZ de PostgreSQL se mapea a OffsetDateTime en Java
    // (incluye la zona horaria, a diferencia de LocalDateTime).
    @Column(name = "desbloqueado_en", nullable = false)
    private OffsetDateTime desbloqueadoEn;

    // @PrePersist se ejecuta justo antes de que Hibernate haga el INSERT.
    // Setea la fecha actual si no fue proporcionada, replicando DEFAULT NOW() del DDL.
    @PrePersist
    public void prePersist() {
        if (desbloqueadoEn == null) {
            desbloqueadoEn = OffsetDateTime.now();
        }
    }
}

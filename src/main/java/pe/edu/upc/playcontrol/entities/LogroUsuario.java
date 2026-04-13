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
        // Esto le dice a la BD que la combinación usuario + logro debe ser única
        // un usuario no puede desbloquear el mismo logro dos veces
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

    // usuario_id es FK a la tabla usuario
    // Se mapea con @ManyToOne para evitar dependencia
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    // logro_id sí se mapea con @ManyToOne porque Logro es nuestra entidad
    // FetchType.LAZY hace que no cargue el Logro completo hasta que lo uses
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logro_id")
    private Logro logro;

    @Column(name = "desbloqueado_en", nullable = false)
    private OffsetDateTime desbloqueadoEn;

    // Setea la fecha actual si no fue proporcionada con el .now()
    @PrePersist
    public void prePersist() {
        if (desbloqueadoEn == null) {
            desbloqueadoEn = OffsetDateTime.now();
        }
    }
}

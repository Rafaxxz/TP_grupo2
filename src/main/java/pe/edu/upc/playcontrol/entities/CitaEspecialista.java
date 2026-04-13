package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cita_especialista")
@Getter @Setter @NoArgsConstructor
public class CitaEspecialista {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cita", updatable = false, nullable = false)
    private UUID idCita;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "especialista_id", nullable = false)
    private Especialista especialista;

    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "pendiente";

    @PrePersist
    public void prePersist() {
        if (estado == null) estado = "pendiente";
    }
}

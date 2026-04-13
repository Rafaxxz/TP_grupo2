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

    //PK del canje
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_canje", updatable = false, nullable = false)
    private UUID idCanje;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    //el LAZY hace que la carga de la recompensa se haga solo cuando se necesita acceder a ella
    //aqui se hace relacion con la tabla recompensa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recompensa_id")
    private Recompensa recompensa;

    //cuantos puntos se usan para canjear la recompensa
    @Column(name = "puntos_usados", nullable = false)
    private int puntosUsados;

    //fecha en la que se realiza el canje
    @Column(name = "canjeado_en", nullable = false)
    private OffsetDateTime canjeadoEn;

    //metodo automatico que se ejecuta antes de insertar en la BD
    //si es nulo, se asigna la fecha y hora actual
    @PrePersist
    public void prePersist() {
        if (canjeadoEn == null) {
            canjeadoEn = OffsetDateTime.now();
        }
    }
}

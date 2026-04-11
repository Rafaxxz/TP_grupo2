package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "recompensa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_recompensa", updatable = false, nullable = false)
    private UUID idRecompensa;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    // tipo tiene CHECK (tipo IN ('avatar','tema','insignia')) en el DDL.
    // Se mapea como String simple.
    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "costo_puntos", nullable = false)
    private int costoPuntos;

    @Column(name = "recurso_url", columnDefinition = "TEXT")
    private String recursoUrl;
}

package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recompensa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recompensa")
    private Integer idRecompensa;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "costo_puntos", nullable = false)
    private int costoPuntos;

    @Column(name = "recurso_url", columnDefinition = "TEXT")
    private String recursoUrl;
}

package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "logro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logro")
    private Integer idLogro;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "icono_url", columnDefinition = "TEXT")
    private String iconoUrl;

    @Column(name = "puntos_otorgados", nullable = false)
    private int puntosOtorgados;

    @Column(name = "criterio", nullable = false, length = 30)
    private String criterio;

    @Column(name = "valor_criterio", nullable = false)
    private int valorCriterio;
}

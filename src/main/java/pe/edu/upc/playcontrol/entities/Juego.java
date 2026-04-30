package pe.edu.upc.playcontrol.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "juego")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego")
    private Integer idJuego;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "plataforma", length = 60)
    private String plataforma;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaJuego categoriaJuego;
}

package pe.edu.upc.playcontrol.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria_juego")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaJuego {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private String nombre;
}

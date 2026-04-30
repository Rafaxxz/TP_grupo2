package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "juego")
<<<<<<< HEAD
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
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
<<<<<<< HEAD
}
=======

    public Juego() {
    }

    public UUID getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(UUID idJuego) {
        this.idJuego = idJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public CategoriaJuego getCategoriaJuego() {
        return categoriaJuego;
    }

    public void setCategoriaJuego(CategoriaJuego categoriaJuego) {
        this.categoriaJuego = categoriaJuego;
    }
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

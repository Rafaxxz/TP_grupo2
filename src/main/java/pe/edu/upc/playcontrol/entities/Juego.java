package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "juego")
public class Juego {

    @Id
    @GeneratedValue
    @Column(name = "id_juego")
    private UUID idJuego;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "plataforma", length = 60)
    private String plataforma;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaJuego categoriaJuego;

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
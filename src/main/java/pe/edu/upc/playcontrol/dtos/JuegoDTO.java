package pe.edu.upc.playcontrol.dtos;

import java.util.UUID;

public class JuegoDTO {

    private UUID idJuego;
    private String nombre;
    private String plataforma;
    private Integer categoriaId;

    public JuegoDTO() {
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

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JuegoDTO {
    private Integer idJuego;

    @NotBlank(message = "El título del juego es obligatorio")
    private String titulo;

    private String descripcion;
    private String urlImagen;

    @NotNull(message = "La categoría es obligatoria")
    private Integer categoriaId;

    private String tipoMecánica;
}
=======
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
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

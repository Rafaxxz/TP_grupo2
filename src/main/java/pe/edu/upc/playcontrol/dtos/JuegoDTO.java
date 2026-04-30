package pe.edu.upc.playcontrol.dtos;

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

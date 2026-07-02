package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategoriaJuegoDTO {

    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;
}

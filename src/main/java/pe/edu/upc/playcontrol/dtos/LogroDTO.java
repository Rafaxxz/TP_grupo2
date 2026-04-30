package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogroDTO {

    private Integer idLogro;

    @NotBlank(message = "El nombre del logro es obligatorio")
    private String nombre;

    private String descripcion;
    private String iconoUrl;
    private int puntosOtorgados;

    @NotBlank(message = "El criterio es obligatorio")
    private String criterio;

    private int valorCriterio;
}

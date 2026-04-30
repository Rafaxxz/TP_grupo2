package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecompensaDTO {

    private Integer idRecompensa;

    @NotBlank(message = "El nombre de la recompensa es obligatorio")
    private String nombre;

    private String descripcion;

    @NotBlank(message = "El tipo de la recompensa es obligatorio")
    private String tipo;

    private int costoPuntos;
    private String recursoUrl;
}

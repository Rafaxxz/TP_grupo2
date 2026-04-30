package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RetoDTO {
    private Integer idReto;

    @NotBlank(message = "El título del reto es obligatorio")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "El tipo del reto es obligatorio")
    private String tipo;

    @NotNull(message = "La duración en días es obligatoria")
    private Integer duracionDias;

    @NotBlank(message = "La dificultad es obligatoria")
    private String dificultad;

    private Boolean activo;
    private Integer recompensaId;
    private Integer logroId;
}

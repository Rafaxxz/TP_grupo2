package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CitaEspecialistaDTO {
    private Integer idCita;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El especialistaId es obligatorio")
    private Integer especialistaId;

    @NotNull(message = "La fecha y hora de la cita es obligatoria")
    private OffsetDateTime fechaHora;

    @NotBlank(message = "El estado de la cita es obligatorio")
    private String estado;
}

package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LimiteTiempoDTO {
    private Integer idLimite;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "El tipo de límite es obligatorio")
    private String tipo;

    @NotNull(message = "Los minutos máximos son obligatorios")
    private Integer minutosMaximos;

    private Boolean bloqueoActivo;
    private Boolean notificar;
    private OffsetDateTime actualizadoEn;
}

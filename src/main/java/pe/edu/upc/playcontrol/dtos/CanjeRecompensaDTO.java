package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CanjeRecompensaDTO {

    private Integer idCanje;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El recompensaId es obligatorio")
    private Integer recompensaId;

    private int puntosUsados;
    private OffsetDateTime canjeadoEn;
}

package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RetoUsuarioDTO {
    private Integer id;

    @NotNull(message = "El retoId es obligatorio")
    private Integer retoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    private OffsetDateTime aceptadoEn;
    private Boolean completado;
    private OffsetDateTime finalizadoEn;
}

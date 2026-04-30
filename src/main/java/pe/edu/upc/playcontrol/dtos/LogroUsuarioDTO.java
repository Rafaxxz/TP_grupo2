package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogroUsuarioDTO {

    private Integer id;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El logroId es obligatorio")
    private Integer logroId;

    private OffsetDateTime desbloqueadoEn;
}

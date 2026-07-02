package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class SesionJuegoDTO {


    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El inicio de la sesión es obligatorio")
    private OffsetDateTime inicio;


}

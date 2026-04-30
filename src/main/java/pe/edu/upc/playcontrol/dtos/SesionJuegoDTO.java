package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class SesionJuegoDTO {
    private Integer idSesion;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El juegoId es obligatorio")
    private Integer juegoId;

    @NotNull(message = "El inicio de la sesión es obligatorio")
    private OffsetDateTime inicio;

    private OffsetDateTime fin;
    private Integer duracionMinutos;
    private LocalDate fecha;
}

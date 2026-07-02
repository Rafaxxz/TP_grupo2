package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class DTO1 {
    private Integer idSesion;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;
    @NotNull(message = "El inicio de la sesión es obligatorio")
    private OffsetDateTime inicio;

}

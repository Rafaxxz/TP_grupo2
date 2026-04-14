package pe.edu.upc.playcontrol.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class SesionJuegoDTO {
    private UUID idSesion;
    private UUID usuarioId;
    private UUID juegoId;
    private OffsetDateTime inicio;
    private OffsetDateTime fin;
    private Integer duracionMinutos;
    private LocalDate fecha;
}
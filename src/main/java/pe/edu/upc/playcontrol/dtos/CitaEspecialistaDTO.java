package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CitaEspecialistaDTO {
    private UUID idCita;
    private UUID usuarioId;
    private UUID especialistaId;
    private OffsetDateTime fechaHora;
    private String estado;
}

package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CanjeRecompensaDTO {

    private UUID idCanje;
    private UUID usuarioId;
    private UUID recompensaId;
    private int puntosUsados;
    private OffsetDateTime canjeadoEn;
}

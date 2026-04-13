package pe.edu.upc.playcontrol.dtos;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class LimiteTiempoDTO {
    private UUID idLimite;
    private UUID usuarioId;
    private String tipo;
    private Integer minutosMaximos;
    private Boolean bloqueoActivo;
    private Boolean notificar;
    private OffsetDateTime actualizadoEn;
}
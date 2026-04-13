package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MensajeDTO {
    private UUID idMensaje;
    private UUID remitenteId;
    private UUID destinatarioId;
    private String contenido;
    private String contexto;
    private OffsetDateTime enviadoEn;
    private Boolean leido;
}

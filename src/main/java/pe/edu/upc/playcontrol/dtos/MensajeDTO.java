package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MensajeDTO {
    private Integer idMensaje;

    @NotNull(message = "El remitenteId es obligatorio")
    private Integer remitenteId;

    @NotNull(message = "El destinatarioId es obligatorio")
    private Integer destinatarioId;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    private String contenido;

    private String contexto;
    private OffsetDateTime enviadoEn;
    private Boolean leido;
}

package pe.edu.upc.playcontrol.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RetoDTO {
    private UUID idReto;
    private String titulo;
    private String descripcion;
    private String tipo;
    private Integer duracionDias;
    private String dificultad;
    private Boolean activo;
    private UUID recompensaId;
    private UUID logroId;
}

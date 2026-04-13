package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EspecialistaDTO {
    private UUID idEspecialista;
    private UUID usuarioId;
    private String especialidad;
    private String modalidad;
    private Boolean verificado;
}

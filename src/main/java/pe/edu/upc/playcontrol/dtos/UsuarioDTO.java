package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private UUID idUsuario;
    private String username;
    private String email;
    private String nombre;
    private String passwordHash;
    private Integer rolId;
    private Integer puntosTotales;
    private Boolean estado;
    private OffsetDateTime createdAt;
}

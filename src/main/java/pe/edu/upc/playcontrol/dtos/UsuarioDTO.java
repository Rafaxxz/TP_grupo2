package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    private String passwordHash;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;

    private Integer puntosTotales;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    private OffsetDateTime createdAt;
}

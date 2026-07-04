package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
=======
>>>>>>> fabrizzio-salvador
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
<<<<<<< HEAD

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

    private Integer padreId;

=======
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private UUID idUsuario;
    private String username;
    private String email;
    private String nombre;
    private String passwordHash;
    private Integer rolId;
    private UUID padreId;
    private Integer puntosTotales;
    private Boolean estado;
>>>>>>> fabrizzio-salvador
    private OffsetDateTime createdAt;
}

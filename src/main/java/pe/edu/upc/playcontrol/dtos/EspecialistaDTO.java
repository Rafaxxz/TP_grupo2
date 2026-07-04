package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
=======
>>>>>>> fabrizzio-salvador
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EspecialistaDTO {
    private Integer idEspecialista;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modalidad;

=======
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EspecialistaDTO {
    private UUID idEspecialista;
    private UUID usuarioId;
    private String especialidad;
    private String modalidad;
>>>>>>> fabrizzio-salvador
    private Boolean verificado;
}

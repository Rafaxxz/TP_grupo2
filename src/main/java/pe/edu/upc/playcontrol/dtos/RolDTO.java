package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
=======
>>>>>>> fabrizzio-salvador
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RolDTO {
    private Integer idRol;
<<<<<<< HEAD

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 20, message = "El nombre del rol no puede superar los 20 caracteres")
=======
>>>>>>> fabrizzio-salvador
    private String nombre;
}

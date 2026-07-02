package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RolDTO {
    private Integer idRol;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 20, message = "El nombre del rol no puede superar los 20 caracteres")
    private String nombre;
}

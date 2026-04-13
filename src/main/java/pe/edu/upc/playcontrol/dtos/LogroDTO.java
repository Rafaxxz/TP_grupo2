package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogroDTO {

    private UUID idLogro;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
    private int puntosOtorgados;
    private String criterio;
    private int valorCriterio;
}

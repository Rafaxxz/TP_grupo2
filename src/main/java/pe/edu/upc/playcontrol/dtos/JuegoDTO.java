package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JuegoDTO {

    private Integer idJuego;
    private String nombre;
    private String plataforma;
    private Integer categoriaId;
}

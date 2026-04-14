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
public class JuegoDTO {
    private UUID idJuego;
    private String titulo;
    private String descripcion;
    private String urlImagen;
    private Integer categoriaId;
    private String tipoMecánica;
}



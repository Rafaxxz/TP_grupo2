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
public class RecompensaDTO {

    private UUID idRecompensa;
    private String nombre;
    private String descripcion;
    private String tipo;           // valores válidos: avatar, tema, insignia
    private int costoPuntos;
    private String recursoUrl;
}

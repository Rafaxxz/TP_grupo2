package pe.edu.upc.playcontrol.dtos;

import lombok.Data;

@Data
public class ReglaJuegoRequest {
    private Integer hijoId;
    private String nombre;
    private String url;
    private String horaInicio;
    private String horaFin;
    private Integer minutosMaximos;
    private Boolean bloqueado;
}

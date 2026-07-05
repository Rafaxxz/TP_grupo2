package pe.edu.upc.playcontrol.dtos;

import lombok.Data;

@Data
public class ReglaJuegoDTO {
    private Integer idRegla;
    private Integer hijoId;
    private String hijoNombre;
    private Integer juegoId;
    private String nombre;
    private String url;
    private String horaInicio;
    private String horaFin;
    private Integer minutosMaximos;
    private Boolean bloqueado;
    private Integer minutosUsadosHoy;
}

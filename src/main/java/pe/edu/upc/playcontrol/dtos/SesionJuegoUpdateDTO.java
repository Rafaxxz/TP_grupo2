package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar sesión de juego en tiempo real vía WebSocket.
 * El hijo envía su progreso mientras juega.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionJuegoUpdateDTO {
    private Integer idUsuario;      // ID del hijo jugando
    private Integer idJuego;        // Juego actual
    private Integer minutosJugados; // Minutos que lleva jugando
    private String estado;          // EN_PROGRESO, PAUSADO, FINALIZADO
    private Long timestamp;         // Timestamp del cliente para sincronización
}


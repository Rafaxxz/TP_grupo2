package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para notificar al hijo sobre estado de límite de tiempo.
 * Enviado por el backend a través de WebSocket.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionLimiteDTO {
    private String tipo;            // ADVERTENCIA, CORTE, OK
    private String mensaje;         // Descripción del estado
    private Integer minutosRestantes; // Minutos restantes (null si corte)
    private Integer idJuego;        // Juego afectado
    private Long timestamp;         // Timestamp del servidor
}


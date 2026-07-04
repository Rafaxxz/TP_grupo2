package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para notificar al padre sobre eventos en tiempo real del hijo.
 * Se envía cuando hay cambios importantes en sesiones o límites.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPadreDTO {
    private String tipo;            // SESION_ACTUALIZADA, JUEGO_CORTADO, ADVERTENCIA_TIEMPO
    private Integer idHijo;         // ID del hijo
    private Integer idJuego;        // Juego involucrado
    private String mensaje;         // Descripción del evento
    private Integer minutosJugados; // Minutos jugados hasta ahora
    private Integer minutosRestantes; // Minutos restantes (si aplica)
    private Long timestamp;         // Timestamp del servidor
}


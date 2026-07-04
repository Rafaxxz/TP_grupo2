package pe.edu.upc.playcontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;

/**
 * Servicio para validar límites de tiempo durante sesiones de juego.
 */
@Service
public class LimiteTiempoValidationService {

    @Autowired
    private LimiteTiempoRepository limiteTiempoRepository;

    /**
     * Obtiene el límite de tiempo activo para un hijo (el más reciente).
     */
    public LimiteTiempo obtenerLimiteActivo(Integer idHijo) {
        return limiteTiempoRepository
                .findFirstByUsuario_IdUsuarioAndBloqueoActivoTrueOrderByActualizadoEnDesc(idHijo)
                .orElse(null);
    }

    /**
     * Calcula minutos restantes de un hijo para un juego.
     * Retorna:
     * - Número positivo: minutos restantes
     * - 0 o negativo: límite alcanzado o excedido
     */
    public Integer calcularMinutosRestantes(Integer minutosJugados, LimiteTiempo limite) {
        if (limite == null || limite.getMinutosMaximos() == null) {
            return Integer.MAX_VALUE; // Sin límite
        }
        return limite.getMinutosMaximos() - minutosJugados;
    }

    /**
     * Determina si se debe enviar una advertencia (5 minutos o menos).
     */
    public boolean debeEnviarAdvertencia(Integer minutosRestantes) {
        return minutosRestantes != null && minutosRestantes > 0 && minutosRestantes <= 5;
    }

    /**
     * Determina si se debe cortar el juego (límite alcanzado o excedido).
     */
    public boolean debeCortar(Integer minutosRestantes) {
        return minutosRestantes != null && minutosRestantes <= 0;
    }
}



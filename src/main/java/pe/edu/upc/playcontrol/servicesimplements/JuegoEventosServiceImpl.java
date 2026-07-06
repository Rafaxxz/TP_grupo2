package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.playcontrol.entities.*;
import pe.edu.upc.playcontrol.repositories.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Procesa eventos al finalizar una sesión: US37 (alerta límite) y US48 (auto-desbloqueo logros).
 */
@Service
public class JuegoEventosServiceImpl {

    @Autowired private IAlertaRepository alertaRepo;
    @Autowired private ILogroRepository logroRepo;
    @Autowired private ILogroUsuarioRepository logroUsuarioRepo;
    @Autowired private SesionJuegoRepository sesionRepo;
    @Autowired private LimiteTiempoRepository limiteRepo;
    @Autowired private IUsuarioRepository usuarioRepo;
    @Autowired private IReglaJuegoRepository reglaJuegoRepo;

    @Transactional
    public List<Logro> procesarFinSesion(SesionJuego sesion) {
        Integer usuarioId = sesion.getUsuario().getIdUsuario();

        // US37: emitir alerta si se superó el límite diario general
        evaluarLimiteDiario(usuarioId, sesion);

        // Emitir alerta si se superó el límite del juego puntual (Control de juegos)
        evaluarLimiteJuego(usuarioId, sesion);

        // US48: desbloquear logros que cumplan criterio
        return desbloquearLogros(usuarioId, sesion);
    }

    // US37 ────────────────────────────────────────────────────────────────────
    private void evaluarLimiteDiario(Integer usuarioId, SesionJuego sesion) {
        limiteRepo.findByUsuario_IdUsuario(usuarioId).stream()
            .filter(l -> "diario".equalsIgnoreCase(l.getTipo()) && Boolean.TRUE.equals(l.getNotificar()))
            .findFirst()
            .ifPresent(limite -> {
                Integer minHoy = sesionRepo.sumMinutosByUsuarioAndFecha(usuarioId, sesion.getFecha());
                if (minHoy != null && minHoy >= limite.getMinutosMaximos()) {
                    // Solo una alerta de límite por día para el mismo usuario
                    boolean yaAlertado = alertaRepo.findByUsuario_IdUsuario(usuarioId).stream()
                        .anyMatch(a -> "limite_excedido".equals(a.getTipo())
                            && a.getEmitidaEn() != null
                            && a.getEmitidaEn().toLocalDate().equals(sesion.getFecha()));
                    if (!yaAlertado) {
                        String nombreHijo = usuarioRepo.findById(usuarioId).map(Usuario::getNombre).orElse("Tu hijo");
                        Alerta a = buildAlerta(usuarioId, sesion, "limite_excedido", "warning",
                            "Has superado tu límite diario de " + limite.getMinutosMaximos() + " min. (" + minHoy + " min jugados hoy).");
                        alertaRepo.save(a);
                        notificarPadre(usuarioId, sesion, "limite_excedido", "warning",
                            nombreHijo + " superó su límite diario de " + limite.getMinutosMaximos() + " min. (" + minHoy + " min jugados hoy).");
                    }
                }
            });
    }

    // Límite por juego específico asignado desde "Control de juegos"
    private void evaluarLimiteJuego(Integer usuarioId, SesionJuego sesion) {
        if (sesion.getJuego() == null) return;
        Integer juegoId = sesion.getJuego().getIdJuego();
        reglaJuegoRepo.findByHijo_IdUsuarioAndJuego_IdJuego(usuarioId, juegoId)
            .filter(regla -> regla.getMinutosMaximos() != null && regla.getMinutosMaximos() > 0)
            .ifPresent(regla -> {
                Integer minHoy = sesionRepo.sumMinutosByUsuarioAndJuegoAndFecha(usuarioId, juegoId, sesion.getFecha());
                if (minHoy != null && minHoy >= regla.getMinutosMaximos()) {
                    boolean yaAlertado = alertaRepo.findByUsuario_IdUsuario(usuarioId).stream()
                        .anyMatch(a -> "limite_juego_excedido".equals(a.getTipo())
                            && juegoId.equals(juegoIdDeAlerta(a))
                            && a.getEmitidaEn() != null
                            && a.getEmitidaEn().toLocalDate().equals(sesion.getFecha()));
                    if (!yaAlertado) {
                        String nombreJuego = regla.getJuego().getNombre();
                        String nombreHijo = usuarioRepo.findById(usuarioId).map(Usuario::getNombre).orElse("Tu hijo");
                        Alerta a = buildAlerta(usuarioId, sesion, "limite_juego_excedido", "warning",
                            "Alcanzaste el límite de " + regla.getMinutosMaximos() + " min. para \"" + nombreJuego + "\" hoy.");
                        alertaRepo.save(a);
                        notificarPadre(usuarioId, sesion, "limite_juego_excedido", "warning",
                            nombreHijo + " alcanzó el límite de " + regla.getMinutosMaximos() + " min. en \"" + nombreJuego + "\" hoy.");
                    }
                }
            });
    }

    private Integer juegoIdDeAlerta(Alerta a) {
        return a.getSesionJuego() != null && a.getSesionJuego().getJuego() != null
            ? a.getSesionJuego().getJuego().getIdJuego() : null;
    }

    // Crea una copia de la alerta para el padre vinculado, si existe.
    private void notificarPadre(Integer hijoId, SesionJuego sesion, String tipo, String nivel, String mensaje) {
        usuarioRepo.findById(hijoId)
            .map(Usuario::getPadreId)
            .filter(padreId -> padreId != null)
            .ifPresent(padreId -> alertaRepo.save(buildAlerta(padreId, sesion, tipo, nivel, mensaje)));
    }

    // US48 ────────────────────────────────────────────────────────────────────
    private List<Logro> desbloquearLogros(Integer usuarioId, SesionJuego sesion) {
        List<Logro> nuevos = new ArrayList<>();

        Set<Integer> yaIds = logroUsuarioRepo.findByUsuarioId(usuarioId).stream()
            .map(lu -> lu.getLogro().getIdLogro())
            .collect(Collectors.toSet());

        List<SesionJuego> todasSesiones = sesionRepo.findByUsuario_IdUsuario(usuarioId);
        long totalSesiones = todasSesiones.stream().filter(s -> s.getFin() != null).count();
        long totalMinutos  = todasSesiones.stream()
            .filter(s -> s.getDuracionMinutos() != null)
            .mapToLong(SesionJuego::getDuracionMinutos).sum();
        long totalLogros   = yaIds.size();

        for (Logro logro : logroRepo.findAll()) {
            if (yaIds.contains(logro.getIdLogro())) continue;

            boolean cumple = switch (logro.getCriterio().toLowerCase()) {
                case "sesiones" -> totalSesiones >= logro.getValorCriterio();
                case "minutos"  -> totalMinutos  >= logro.getValorCriterio();
                case "logros"   -> totalLogros   >= logro.getValorCriterio();
                default -> false;
            };

            if (cumple) {
                LogroUsuario lu = new LogroUsuario();
                lu.setUsuarioId(usuarioId);
                lu.setLogro(logro);
                lu.setDesbloqueadoEn(OffsetDateTime.now());
                logroUsuarioRepo.save(lu);

                // Sumar puntos al usuario
                usuarioRepo.findById(usuarioId).ifPresent(u -> {
                    int puntosActuales = u.getPuntosTotales() != null ? u.getPuntosTotales() : 0;
                    u.setPuntosTotales(puntosActuales + logro.getPuntosOtorgados());
                    usuarioRepo.save(u);
                });

                // Alerta de logro desbloqueado
                Alerta a = buildAlerta(usuarioId, sesion, "logro_desbloqueado", "info",
                    "¡Logro desbloqueado: " + logro.getNombre() + "! +" + logro.getPuntosOtorgados() + " puntos.");
                alertaRepo.save(a);

                nuevos.add(logro);
                yaIds.add(logro.getIdLogro()); // actualizar para criterio "logros" en siguiente iteración
                totalLogros++;
            }
        }
        return nuevos;
    }

    private Alerta buildAlerta(Integer usuarioId, SesionJuego sesion, String tipo, String nivel, String mensaje) {
        Alerta a = new Alerta();
        Usuario u = new Usuario(); u.setIdUsuario(usuarioId);
        a.setUsuario(u);
        a.setSesionJuego(sesion);
        a.setTipo(tipo);
        a.setNivel(nivel);
        a.setMensaje(mensaje);
        a.setLeida(false);
        a.setEmitidaEn(OffsetDateTime.now());
        return a;
    }
}

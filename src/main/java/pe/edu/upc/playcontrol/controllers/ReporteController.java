package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.playcontrol.dtos.ConteoDTO;
import pe.edu.upc.playcontrol.repositories.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
public class ReporteController {

    @Autowired private IUsuarioRepository usuarioRepo;
    @Autowired private IEspecialistaRepository especialistaRepo;
    @Autowired private IJuegoRepository juegoRepo;
    @Autowired private SesionJuegoRepository sesionRepo;
    @Autowired private ICanjeRecompensaRepository canjeRepo;
    @Autowired private ILogroUsuarioRepository logroUsuarioRepo;
    @Autowired private ICitaEspecialistaRepository citaRepo;
    @Autowired private IContenidoEducativoRepository contenidoRepo;

    private List<ConteoDTO> mapear(List<Object[]> filas) {
        return filas.stream()
                .map(f -> new ConteoDTO(f[0] == null ? "Sin definir" : f[0].toString(),
                                        ((Number) f[1]).longValue()))
                .toList();
    }

    @GetMapping("/usuarios-por-rol")
    public ResponseEntity<List<ConteoDTO>> usuariosPorRol() {
        return ResponseEntity.ok(mapear(usuarioRepo.contarUsuariosPorRol()));
    }

    @GetMapping("/especialistas-por-modalidad")
    public ResponseEntity<List<ConteoDTO>> especialistasPorModalidad() {
        return ResponseEntity.ok(mapear(especialistaRepo.contarEspecialistasPorModalidad()));
    }

    @GetMapping("/juegos-por-categoria")
    public ResponseEntity<List<ConteoDTO>> juegosPorCategoria() {
        return ResponseEntity.ok(mapear(juegoRepo.contarJuegosPorCategoria()));
    }

    @GetMapping("/minutos-por-usuario")
    public ResponseEntity<List<ConteoDTO>> minutosPorUsuario() {
        return ResponseEntity.ok(mapear(sesionRepo.sumarMinutosPorUsuario()));
    }

    @GetMapping("/recompensas-mas-canjeadas")
    public ResponseEntity<List<ConteoDTO>> recompensasMasCanjeadas() {
        return ResponseEntity.ok(mapear(canjeRepo.recompensasMasCanjeadas()));
    }

    @GetMapping("/logros-mas-desbloqueados")
    public ResponseEntity<List<ConteoDTO>> logrosMasDesbloqueados() {
        return ResponseEntity.ok(mapear(logroUsuarioRepo.logrosMasDesbloqueados()));
    }

    @GetMapping("/citas-por-estado")
    public ResponseEntity<List<ConteoDTO>> citasPorEstado() {
        return ResponseEntity.ok(mapear(citaRepo.contarCitasPorEstado()));
    }

    @GetMapping("/contenido-por-tipo")
    public ResponseEntity<List<ConteoDTO>> contenidoPorTipo() {
        return ResponseEntity.ok(mapear(contenidoRepo.contarContenidosPorTipo()));
    }
}

package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.playcontrol.repositories.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'HIJO')")
public class ConsultaController {

    @Autowired private IUsuarioRepository usuarioRepo;
    @Autowired private IEspecialistaRepository especialistaRepo;
    @Autowired private IJuegoRepository juegoRepo;
    @Autowired private SesionJuegoRepository sesionRepo;
    @Autowired private ILogroUsuarioRepository logroUsuarioRepo;
    @Autowired private ICanjeRecompensaRepository canjeRepo;
    @Autowired private IRetoUsuarioRepository retoUsuarioRepo;
    @Autowired private ICitaEspecialistaRepository citaRepo;

    private List<Map<String, Object>> filas(List<Object[]> datos, String... columnas) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (Object[] fila : datos) {
            Map<String, Object> m = new LinkedHashMap<>();
            for (int i = 0; i < columnas.length; i++) {
                m.put(columnas[i], i < fila.length ? fila[i] : null);
            }
            res.add(m);
        }
        return res;
    }

    @GetMapping("/usuarios-por-rol")
    public ResponseEntity<?> usuariosPorRol(@RequestParam String q) {
        return ResponseEntity.ok(filas(usuarioRepo.buscarUsuariosPorRol(q), "Usuario", "Nombre", "Rol"));
    }

    @GetMapping("/especialistas-por-nombre")
    public ResponseEntity<?> especialistasPorNombre(@RequestParam String q) {
        return ResponseEntity.ok(filas(especialistaRepo.buscarEspecialistasPorNombre(q), "Nombre", "Especialidad", "Modalidad"));
    }

    @GetMapping("/juegos-por-categoria")
    public ResponseEntity<?> juegosPorCategoria(@RequestParam String q) {
        return ResponseEntity.ok(filas(juegoRepo.buscarJuegosPorCategoria(q), "Juego", "Plataforma", "Categoría"));
    }

    @GetMapping("/sesiones-por-usuario")
    public ResponseEntity<?> sesionesPorUsuario(@RequestParam String q) {
        return ResponseEntity.ok(filas(sesionRepo.buscarSesionesPorUsuario(q), "Usuario", "Juego", "Fecha", "Minutos"));
    }

    @GetMapping("/logros-por-usuario")
    public ResponseEntity<?> logrosPorUsuario(@RequestParam String q) {
        return ResponseEntity.ok(filas(logroUsuarioRepo.buscarLogrosPorUsuario(q), "Usuario", "Logro", "Puntos", "Desbloqueado"));
    }

    @GetMapping("/canjes-por-recompensa")
    public ResponseEntity<?> canjesPorRecompensa(@RequestParam String q) {
        return ResponseEntity.ok(filas(canjeRepo.buscarCanjesPorRecompensa(q), "Usuario", "Recompensa", "Puntos usados", "Fecha"));
    }

    @GetMapping("/retos-por-usuario")
    public ResponseEntity<?> retosPorUsuario(@RequestParam String q) {
        return ResponseEntity.ok(filas(retoUsuarioRepo.buscarRetosPorUsuario(q), "Usuario", "Reto", "Completado", "Aceptado"));
    }

    @GetMapping("/citas-por-estado")
    public ResponseEntity<?> citasPorEstado(@RequestParam String q) {
        return ResponseEntity.ok(filas(citaRepo.buscarCitasPorEstado(q), "Usuario", "Especialidad", "Fecha", "Estado"));
    }
}

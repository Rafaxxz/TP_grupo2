package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.ICanjeRecompensaRepository;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla canje_recompensa
@Service
public class CanjeRecompensaServiceImplement implements ICanjeRecompensaService {

    @Autowired
    private ICanjeRecompensaRepository canjeRecompensaRepository;

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<CanjeRecompensaDTO> list() {
        return canjeRecompensaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CanjeRecompensaDTO insert(CanjeRecompensaDTO dto) {
        Recompensa recompensa = dto.getRecompensaId() != null
                ? recompensaRepository.findById(dto.getRecompensaId()).orElse(null) : null;
        if (recompensa == null) throw new IllegalArgumentException("Recompensa no encontrada");

        Usuario usuario = dto.getUsuarioId() != null
                ? usuarioRepository.findById(dto.getUsuarioId()).orElse(null) : null;
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado");

        int costo = recompensa.getCostoPuntos();
        int puntosActuales = usuario.getPuntosTotales() != null ? usuario.getPuntosTotales() : 0;
        if (puntosActuales < costo) {
            throw new IllegalArgumentException("Puntos insuficientes. Tienes " + puntosActuales + " pts, necesitas " + costo + " pts.");
        }

        dto.setPuntosUsados(costo);
        usuario.setPuntosTotales(puntosActuales - costo);
        usuarioRepository.save(usuario);

        return toDTO(canjeRecompensaRepository.save(toEntity(dto)));
    }

    @Override
    public CanjeRecompensaDTO update(CanjeRecompensaDTO dto) {
        return toDTO(canjeRecompensaRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<CanjeRecompensaDTO> listId(Integer id) {
        return canjeRecompensaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(Integer id) {
        canjeRecompensaRepository.deleteById(id);
    }

    @Override
    public List<CanjeRecompensaDTO> listByUsuarioId(Integer usuarioId) {
        return canjeRecompensaRepository.findByUsuarioId(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public int totalPuntosGastadosPorUsuario(Integer usuarioId) {
        List<CanjeRecompensa> canjes = canjeRecompensaRepository.findByUsuarioId(usuarioId);
        return canjes.stream().mapToInt(CanjeRecompensa::getPuntosUsados).sum();
    }

    @Override
    public Object balanceActualPuntos(Integer usuarioId) {
        return canjeRecompensaRepository.balanceActualPuntos(usuarioId);
    }

    @Override
    public Object historialCanjesVsDisponibles(Integer usuarioId) {
        return canjeRecompensaRepository.historialCanjesVsDisponibles(usuarioId);
    }

    private CanjeRecompensaDTO toDTO(CanjeRecompensa e) {
        CanjeRecompensaDTO dto = new CanjeRecompensaDTO();
        dto.setIdCanje(e.getIdCanje());
        dto.setUsuarioId(e.getUsuarioId());
        dto.setRecompensaId(e.getRecompensa() != null ? e.getRecompensa().getIdRecompensa() : null);
        dto.setPuntosUsados(e.getPuntosUsados());
        dto.setCanjeadoEn(e.getCanjeadoEn());
        return dto;
    }

    private CanjeRecompensa toEntity(CanjeRecompensaDTO dto) {
        CanjeRecompensa e = new CanjeRecompensa();
        e.setIdCanje(dto.getIdCanje());
        e.setUsuarioId(dto.getUsuarioId());
        if (dto.getRecompensaId() != null) {
            Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId()).orElse(null);
            e.setRecompensa(recompensa);
        }
        e.setPuntosUsados(dto.getPuntosUsados());
        e.setCanjeadoEn(dto.getCanjeadoEn());
        return e;
    }
}

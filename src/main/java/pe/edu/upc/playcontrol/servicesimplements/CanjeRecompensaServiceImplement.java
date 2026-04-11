package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.repositories.ICanjeRecompensaRepository;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CanjeRecompensaServiceImplement implements ICanjeRecompensaService {

    @Autowired
    private ICanjeRecompensaRepository canjeRecompensaRepository;

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Override
    public List<CanjeRecompensaDTO> getAll() {
        return canjeRecompensaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CanjeRecompensaDTO getById(UUID id) {
        CanjeRecompensa canje = canjeRecompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CanjeRecompensa no encontrado con id: " + id));
        return toDto(canje);
    }

    @Override
    public List<CanjeRecompensaDTO> getByUsuarioId(UUID usuarioId) {
        return canjeRecompensaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CanjeRecompensaDTO save(CanjeRecompensaDTO dto) {
        CanjeRecompensa canje = new CanjeRecompensa();
        canje.setUsuarioId(dto.getUsuarioId());
        Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId())
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + dto.getRecompensaId()));
        canje.setRecompensa(recompensa);
        canje.setPuntosUsados(dto.getPuntosUsados());
        // canjeadoEn se asigna automáticamente en @PrePersist
        return toDto(canjeRecompensaRepository.save(canje));
    }

    @Override
    public void delete(UUID id) {
        canjeRecompensaRepository.deleteById(id);
    }

    private CanjeRecompensaDTO toDto(CanjeRecompensa canje) {
        CanjeRecompensaDTO dto = new CanjeRecompensaDTO();
        dto.setIdCanje(canje.getIdCanje());
        dto.setUsuarioId(canje.getUsuarioId());
        dto.setRecompensaId(canje.getRecompensa() != null ? canje.getRecompensa().getIdRecompensa() : null);
        dto.setPuntosUsados(canje.getPuntosUsados());
        dto.setCanjeadoEn(canje.getCanjeadoEn());
        return dto;
    }
}

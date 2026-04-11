package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecompensaServiceImplement implements IRecompensaService {

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Override
    public List<RecompensaDTO> getAll() {
        return recompensaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecompensaDTO getById(UUID id) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + id));
        return toDto(recompensa);
    }

    @Override
    public RecompensaDTO save(RecompensaDTO dto) {
        return toDto(recompensaRepository.save(toEntity(dto)));
    }

    @Override
    public RecompensaDTO update(UUID id, RecompensaDTO dto) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + id));
        recompensa.setNombre(dto.getNombre());
        recompensa.setDescripcion(dto.getDescripcion());
        recompensa.setTipo(dto.getTipo());
        recompensa.setCostoPuntos(dto.getCostoPuntos());
        recompensa.setRecursoUrl(dto.getRecursoUrl());
        return toDto(recompensaRepository.save(recompensa));
    }

    @Override
    public void delete(UUID id) {
        recompensaRepository.deleteById(id);
    }

    private RecompensaDTO toDto(Recompensa recompensa) {
        RecompensaDTO dto = new RecompensaDTO();
        dto.setIdRecompensa(recompensa.getIdRecompensa());
        dto.setNombre(recompensa.getNombre());
        dto.setDescripcion(recompensa.getDescripcion());
        dto.setTipo(recompensa.getTipo());
        dto.setCostoPuntos(recompensa.getCostoPuntos());
        dto.setRecursoUrl(recompensa.getRecursoUrl());
        return dto;
    }

    private Recompensa toEntity(RecompensaDTO dto) {
        Recompensa recompensa = new Recompensa();
        recompensa.setNombre(dto.getNombre());
        recompensa.setDescripcion(dto.getDescripcion());
        recompensa.setTipo(dto.getTipo());
        recompensa.setCostoPuntos(dto.getCostoPuntos());
        recompensa.setRecursoUrl(dto.getRecursoUrl());
        return recompensa;
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla recompensa
@Service
public class RecompensaServiceImplement implements IRecompensaService {

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Override
    public List<RecompensaDTO> list() {
        return recompensaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RecompensaDTO insert(RecompensaDTO dto) {
        return toDTO(recompensaRepository.save(toEntity(dto)));
    }

    @Override
    public RecompensaDTO update(RecompensaDTO dto) {
        return toDTO(recompensaRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<RecompensaDTO> listId(Integer id) {
        return recompensaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(Integer id) {
        recompensaRepository.deleteById(id);
    }

    @Override
    public List<RecompensaDTO> listByTipo(String tipo) {
        return recompensaRepository.findByTipo(tipo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RecompensaDTO> findDisponiblesPorPuntos(Integer puntosDisponibles) {
        return recompensaRepository.findDisponiblesPorPuntos(puntosDisponibles).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Object findEstadisticasPorTipo() {
        return recompensaRepository.findEstadisticasPorTipo();
    }

    private RecompensaDTO toDTO(Recompensa e) {
        RecompensaDTO dto = new RecompensaDTO();
        dto.setIdRecompensa(e.getIdRecompensa());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setTipo(e.getTipo());
        dto.setCostoPuntos(e.getCostoPuntos());
        dto.setRecursoUrl(e.getRecursoUrl());
        return dto;
    }

    private Recompensa toEntity(RecompensaDTO dto) {
        Recompensa e = new Recompensa();
        e.setIdRecompensa(dto.getIdRecompensa());
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setTipo(dto.getTipo());
        e.setCostoPuntos(dto.getCostoPuntos());
        e.setRecursoUrl(dto.getRecursoUrl());
        return e;
    }
}

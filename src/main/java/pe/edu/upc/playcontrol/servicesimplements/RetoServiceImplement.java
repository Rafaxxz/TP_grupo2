package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RetoDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.entities.Reto;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.repositories.IRetoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RetoServiceImplement implements IRetoService {

    @Autowired
    private IRetoRepository retoRepository;

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<RetoDTO> getAll() {
        return retoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RetoDTO> getById(UUID id) {
        return retoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public RetoDTO save(RetoDTO dto) {
        Reto entity = toEntity(dto);
        return toDTO(retoRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        retoRepository.deleteById(id);
    }

    private RetoDTO toDTO(Reto e) {
        RetoDTO dto = new RetoDTO();
        dto.setIdReto(e.getIdReto());
        dto.setTitulo(e.getTitulo());
        dto.setDescripcion(e.getDescripcion());
        dto.setTipo(e.getTipo());
        dto.setDuracionDias(e.getDuracionDias());
        dto.setDificultad(e.getDificultad());
        dto.setActivo(e.getActivo());
        dto.setRecompensaId(e.getRecompensa() != null ? e.getRecompensa().getIdRecompensa() : null);
        dto.setLogroId(e.getLogro() != null ? e.getLogro().getIdLogro() : null);
        return dto;
    }

    private Reto toEntity(RetoDTO dto) {
        Reto e = new Reto();
        e.setIdReto(dto.getIdReto());
        e.setTitulo(dto.getTitulo());
        e.setDescripcion(dto.getDescripcion());
        e.setTipo(dto.getTipo());
        e.setDuracionDias(dto.getDuracionDias());
        e.setDificultad(dto.getDificultad());
        e.setActivo(dto.getActivo());
        if (dto.getRecompensaId() != null) {
            Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId()).orElse(null);
            e.setRecompensa(recompensa);
        }
        if (dto.getLogroId() != null) {
            Logro logro = logroRepository.findById(dto.getLogroId()).orElse(null);
            e.setLogro(logro);
        }
        return e;
    }
}

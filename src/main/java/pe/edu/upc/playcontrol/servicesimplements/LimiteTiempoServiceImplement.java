package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LimiteTiempoServiceImplement implements LimiteTiempoService {

    @Autowired
    private LimiteTiempoRepository limiteTiempoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<LimiteTiempoDTO> getAll() {
        return limiteTiempoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<LimiteTiempoDTO> getById(UUID id) {
        return limiteTiempoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public LimiteTiempoDTO save(LimiteTiempoDTO dto) {
        return toDTO(limiteTiempoRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        limiteTiempoRepository.deleteById(id);
    }

    private LimiteTiempoDTO toDTO(LimiteTiempo e) {
        LimiteTiempoDTO dto = new LimiteTiempoDTO();
        dto.setIdLimite(e.getIdLimite());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setTipo(e.getTipo());
        dto.setMinutosMaximos(e.getMinutosMaximos());
        dto.setBloqueoActivo(e.getBloqueoActivo());
        dto.setNotificar(e.getNotificar());
        dto.setActualizadoEn(e.getActualizadoEn());
        return dto;
    }

    private LimiteTiempo toEntity(LimiteTiempoDTO dto) {
        LimiteTiempo e = new LimiteTiempo();
        e.setIdLimite(dto.getIdLimite());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }

        e.setTipo(dto.getTipo());
        e.setMinutosMaximos(dto.getMinutosMaximos());
        e.setBloqueoActivo(dto.getBloqueoActivo());
        e.setNotificar(dto.getNotificar());
        e.setActualizadoEn(dto.getActualizadoEn());

        return e;
    }
}

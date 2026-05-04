package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.entities.LogroUsuario;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.repositories.ILogroUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla logro_usuario
@Service
public class LogroUsuarioServiceImplement implements ILogroUsuarioService {

    @Autowired
    private ILogroUsuarioRepository logroUsuarioRepository;

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<LogroUsuarioDTO> list() {
        return logroUsuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public LogroUsuarioDTO insert(LogroUsuarioDTO dto) {
        return toDTO(logroUsuarioRepository.save(toEntity(dto)));
    }

    @Override
    public LogroUsuarioDTO update(LogroUsuarioDTO dto) {
        return toDTO(logroUsuarioRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<LogroUsuarioDTO> listId(Integer id) {
        return logroUsuarioRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(Integer id) {
        logroUsuarioRepository.deleteById(id);
    }

    @Override
    public List<LogroUsuarioDTO> listByUsuarioId(Integer usuarioId) {
        return logroUsuarioRepository.findByUsuarioId(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public long contarLogrosPorUsuario(Integer usuarioId) {
        return logroUsuarioRepository.findByUsuarioId(usuarioId).size();
    }

    @Override
    public Object dashboardProgresoLogros(Integer usuarioId) {
        return logroUsuarioRepository.dashboardProgresoLogros(usuarioId);
    }

    @Override
    public Object findTimelineDesbloqueos(Integer usuarioId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {
        return logroUsuarioRepository.findTimelineDesbloqueos(usuarioId, fechaInicio, fechaFin);
    }

    private LogroUsuarioDTO toDTO(LogroUsuario e) {
        LogroUsuarioDTO dto = new LogroUsuarioDTO();
        dto.setId(e.getId());
        dto.setUsuarioId(e.getUsuarioId());
        dto.setLogroId(e.getLogro() != null ? e.getLogro().getIdLogro() : null);
        dto.setDesbloqueadoEn(e.getDesbloqueadoEn());
        return dto;
    }

    private LogroUsuario toEntity(LogroUsuarioDTO dto) {
        LogroUsuario e = new LogroUsuario();
        e.setId(dto.getId());
        e.setUsuarioId(dto.getUsuarioId());
        if (dto.getLogroId() != null) {
            Logro logro = logroRepository.findById(dto.getLogroId()).orElse(null);
            e.setLogro(logro);
        }
        e.setDesbloqueadoEn(dto.getDesbloqueadoEn());
        return e;
    }
}

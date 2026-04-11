package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LogroUsuarioDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.entities.LogroUsuario;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.repositories.ILogroUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroUsuarioService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogroUsuarioServiceImplement implements ILogroUsuarioService {

    @Autowired
    private ILogroUsuarioRepository logroUsuarioRepository;

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<LogroUsuarioDTO> getAll() {
        return logroUsuarioRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogroUsuarioDTO getById(UUID id) {
        LogroUsuario lu = logroUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LogroUsuario no encontrado con id: " + id));
        return toDto(lu);
    }

    @Override
    public List<LogroUsuarioDTO> getByUsuarioId(UUID usuarioId) {
        return logroUsuarioRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogroUsuarioDTO save(LogroUsuarioDTO dto) {
        LogroUsuario lu = new LogroUsuario();
        lu.setUsuarioId(dto.getUsuarioId());
        Logro logro = logroRepository.findById(dto.getLogroId())
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + dto.getLogroId()));
        lu.setLogro(logro);
        // desbloqueadoEn se asigna automáticamente en @PrePersist
        return toDto(logroUsuarioRepository.save(lu));
    }

    @Override
    public void delete(UUID id) {
        logroUsuarioRepository.deleteById(id);
    }

    private LogroUsuarioDTO toDto(LogroUsuario lu) {
        LogroUsuarioDTO dto = new LogroUsuarioDTO();
        dto.setId(lu.getId());
        dto.setUsuarioId(lu.getUsuarioId());
        dto.setLogroId(lu.getLogro() != null ? lu.getLogro().getIdLogro() : null);
        dto.setDesbloqueadoEn(lu.getDesbloqueadoEn());
        return dto;
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RetoUsuarioDTO;
import pe.edu.upc.playcontrol.entities.Reto;
import pe.edu.upc.playcontrol.entities.RetoUsuario;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IRetoRepository;
import pe.edu.upc.playcontrol.repositories.IRetoUsuarioRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRetoUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RetoUsuarioServiceImplement implements IRetoUsuarioService {

    @Autowired
    private IRetoUsuarioRepository retoUsuarioRepository;

    @Autowired
    private IRetoRepository retoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<RetoUsuarioDTO> list() {
        return retoUsuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RetoUsuarioDTO insert(RetoUsuarioDTO dto) {
        return toDTO(retoUsuarioRepository.save(toEntity(dto)));
    }

    @Override
    public RetoUsuarioDTO update(RetoUsuarioDTO dto) {
        return toDTO(retoUsuarioRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<RetoUsuarioDTO> listId(UUID id) {
        return retoUsuarioRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(UUID id) {
        retoUsuarioRepository.deleteById(id);
    }

    private RetoUsuarioDTO toDTO(RetoUsuario e) {
        RetoUsuarioDTO dto = new RetoUsuarioDTO();
        dto.setId(e.getId());
        dto.setRetoId(e.getReto() != null ? e.getReto().getIdReto() : null);
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setAceptadoEn(e.getAceptadoEn());
        dto.setCompletado(e.getCompletado());
        dto.setFinalizadoEn(e.getFinalizadoEn());
        return dto;
    }

    private RetoUsuario toEntity(RetoUsuarioDTO dto) {
        RetoUsuario e = new RetoUsuario();
        e.setId(dto.getId());
        if (dto.getRetoId() != null) {
            Reto reto = retoRepository.findById(dto.getRetoId()).orElse(null);
            e.setReto(reto);
        }
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }
        e.setCompletado(dto.getCompletado());
        e.setFinalizadoEn(dto.getFinalizadoEn());
        return e;
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.EspecialistaDTO;
import pe.edu.upc.playcontrol.entities.Especialista;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IEspecialistaRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IEspecialistaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EspecialistaServiceImplement implements IEspecialistaService {

    @Autowired
    private IEspecialistaRepository especialistaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<EspecialistaDTO> getAll() {
        return especialistaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<EspecialistaDTO> getById(UUID id) {
        return especialistaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EspecialistaDTO save(EspecialistaDTO dto) {
        return toDTO(especialistaRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        especialistaRepository.deleteById(id);
    }

    @Override
    public List<Especialista> findByVerificateTrue() {
        return especialistaRepository.findByVerificateTrue();
    }

    private EspecialistaDTO toDTO(Especialista e) {
        EspecialistaDTO dto = new EspecialistaDTO();
        dto.setIdEspecialista(e.getIdEspecialista());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setEspecialidad(e.getEspecialidad());
        dto.setModalidad(e.getModalidad());
        dto.setVerificado(e.getVerificado());
        return dto;
    }

    private Especialista toEntity(EspecialistaDTO dto) {
        Especialista e = new Especialista();
        e.setIdEspecialista(dto.getIdEspecialista());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }
        e.setEspecialidad(dto.getEspecialidad());
        e.setModalidad(dto.getModalidad());
        e.setVerificado(dto.getVerificado());
        return e;
    }
}

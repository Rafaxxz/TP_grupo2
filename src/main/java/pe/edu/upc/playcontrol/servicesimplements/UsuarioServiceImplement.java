package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDTO> getById(UUID id) {
        return usuarioRepository.findById(id).map(this::toDTO);
    }

    @Override
    public UsuarioDTO save(UsuarioDTO dto) {
        return toDTO(usuarioRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(e.getIdUsuario());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setNombre(e.getNombre());
        dto.setPasswordHash(e.getPasswordHash());
        dto.setEstado(e.getEstado());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario e = new Usuario();
        e.setIdUsuario(dto.getIdUsuario());
        e.setUsername(dto.getUsername());
        e.setEmail(dto.getEmail());
        e.setNombre(dto.getNombre());
        e.setPasswordHash(dto.getPasswordHash());
        e.setEstado(dto.getEstado());
        return e;
    }
}

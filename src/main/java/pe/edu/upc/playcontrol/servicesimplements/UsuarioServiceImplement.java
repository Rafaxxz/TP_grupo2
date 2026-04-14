package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Rol;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
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

    @Autowired
    private IRolRepository rolRepository;

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
        dto.setRolId(e.getRol() != null ? e.getRol().getIdRol() : null);
        dto.setPadreId(e.getPadre() != null ? e.getPadre().getIdUsuario() : null);
        dto.setPuntosTotales(e.getPuntosTotales());
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
        if (dto.getRolId() != null) {
            Rol rol = rolRepository.findById(dto.getRolId()).orElse(null);
            e.setRol(rol);
        }
        if (dto.getPadreId() != null) {
            Usuario padre = usuarioRepository.findById(dto.getPadreId()).orElse(null);
            e.setPadre(padre);
        }
        e.setPuntosTotales(dto.getPuntosTotales());
        e.setEstado(dto.getEstado());
        return e;
    }
}

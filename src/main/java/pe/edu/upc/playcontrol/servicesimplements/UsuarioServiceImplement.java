package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Rol;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDTO> getById(Integer id) {
        return usuarioRepository.findById(id).map(this::toDTO);
    }

    @Override
    public UsuarioDTO save(UsuarioDTO dto) {
        Optional<Usuario> existingByUsername = usuarioRepository.findByUsername(dto.getUsername());
        if (existingByUsername.isPresent() && !existingByUsername.get().getIdUsuario().equals(dto.getIdUsuario())) {
            throw new IllegalArgumentException("El username '" + dto.getUsername() + "' ya está registrado");
        }

        Optional<Usuario> existingByEmail = usuarioRepository.findByEmail(dto.getEmail());
        if (existingByEmail.isPresent() && !existingByEmail.get().getIdUsuario().equals(dto.getIdUsuario())) {
            throw new IllegalArgumentException("El email '" + dto.getEmail() + "' ya está registrado");
        }

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + dto.getRolId()));

        String rolNombre = rol.getNombre().toUpperCase();
        if ("ADMIN".equals(rolNombre)) {
            throw new IllegalArgumentException("No se puede asignar el rol ADMIN a través de la API");
        }
        if (!"PADRE".equals(rolNombre) && !"HIJO".equals(rolNombre)) {
            throw new IllegalArgumentException("Solo se permiten los roles PADRE e HIJO");
        }

        return toDTO(usuarioRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> findLastUsers() {
        OffsetDateTime last30days = OffsetDateTime.now().minusDays(30);
        return usuarioRepository.findLastUsers(last30days).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> findByRolNombre(String nombre) {
        return usuarioRepository.findByRolNombre(nombre).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UsuarioDTO toDTO(Usuario e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(e.getIdUsuario());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setNombre(e.getNombre());
        dto.setRolId(e.getIdRol());
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
        e.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        e.setIdRol(dto.getRolId());
        e.setPuntosTotales(dto.getPuntosTotales());
        e.setEstado(dto.getEstado());
        return e;
    }
}

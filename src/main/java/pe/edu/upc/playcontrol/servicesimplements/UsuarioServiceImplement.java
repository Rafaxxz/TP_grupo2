package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
=======
>>>>>>> fabrizzio-salvador
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Rol;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IUsuarioService;
<<<<<<< HEAD

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
=======
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
>>>>>>> fabrizzio-salvador
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

<<<<<<< HEAD
    @Autowired
    private PasswordEncoder passwordEncoder;

=======
>>>>>>> fabrizzio-salvador
    @Override
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
<<<<<<< HEAD
    public Optional<UsuarioDTO> getById(Integer id) {
=======
    public Optional<UsuarioDTO> getById(UUID id) {
>>>>>>> fabrizzio-salvador
        return usuarioRepository.findById(id).map(this::toDTO);
    }

    @Override
<<<<<<< HEAD
    public Optional<UsuarioDTO> findByUsername(String username) {
        return usuarioRepository.findByUsername(username).map(this::toDTO);
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

=======
    public UsuarioDTO save(UsuarioDTO dto) {
>>>>>>> fabrizzio-salvador
        return toDTO(usuarioRepository.save(toEntity(dto)));
    }

    @Override
<<<<<<< HEAD
    public void delete(Integer id) {
=======
    public void delete(UUID id) {
>>>>>>> fabrizzio-salvador
        usuarioRepository.deleteById(id);
    }

    @Override
<<<<<<< HEAD
    public List<UsuarioDTO> findLastUsers() {
        OffsetDateTime last30days = OffsetDateTime.now().minusDays(30);
        return usuarioRepository.findLastUsers(last30days).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> findByRolNombre(String nombre) {
        return usuarioRepository.findByRolNombre(nombre).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> usuariosRegistradosPorMes() {
        return usuarioRepository.usuariosRegistradosPorMes().stream()
                .map(f -> Map.of("mes", f[0], "total", f[1]))
                .collect(Collectors.toList());
=======
    public List<Usuario> findLastUsers() {
        OffsetDateTime Last30days = OffsetDateTime.now().minusDays(30);
        return usuarioRepository.findLastUsers(Last30days);
    }

    @Override
    public List<Usuario> findByRolNombre(String nombre) {
        return usuarioRepository.findByRolNombre(nombre);
>>>>>>> fabrizzio-salvador
    }

    private UsuarioDTO toDTO(Usuario e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(e.getIdUsuario());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setNombre(e.getNombre());
<<<<<<< HEAD
        dto.setRolId(e.getIdRol());
        dto.setPuntosTotales(e.getPuntosTotales());
        dto.setEstado(e.getEstado());
        dto.setPadreId(e.getPadreId());
=======
        dto.setPasswordHash(e.getPasswordHash());
        dto.setRolId(e.getRol() != null ? e.getRol().getIdRol() : null);
        dto.setPadreId(e.getPadre() != null ? e.getPadre().getIdUsuario() : null);
        dto.setPuntosTotales(e.getPuntosTotales());
        dto.setEstado(e.getEstado());
>>>>>>> fabrizzio-salvador
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario e = new Usuario();
        e.setIdUsuario(dto.getIdUsuario());
        e.setUsername(dto.getUsername());
        e.setEmail(dto.getEmail());
        e.setNombre(dto.getNombre());
<<<<<<< HEAD
        e.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        e.setIdRol(dto.getRolId());
        e.setPuntosTotales(dto.getPuntosTotales());
        e.setEstado(dto.getEstado());
        e.setPadreId(dto.getPadreId());
=======
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
>>>>>>> fabrizzio-salvador
        return e;
    }
}

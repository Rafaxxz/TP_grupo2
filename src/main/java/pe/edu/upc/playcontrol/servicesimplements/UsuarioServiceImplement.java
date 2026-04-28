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
        // Validar que username no esté duplicado
        Optional<Usuario> existingByUsername = usuarioRepository.findByUsername(dto.getUsername());
        if (existingByUsername.isPresent() && !existingByUsername.get().getIdUsuario().equals(dto.getIdUsuario())) {
            throw new IllegalArgumentException("El username '" + dto.getUsername() + "' ya está registrado");
        }

        // Validar que email no esté duplicado
        Optional<Usuario> existingByEmail = usuarioRepository.findByEmail(dto.getEmail());
        if (existingByEmail.isPresent() && !existingByEmail.get().getIdUsuario().equals(dto.getIdUsuario())) {
            throw new IllegalArgumentException("El email '" + dto.getEmail() + "' ya está registrado");
        }

        return toDTO(usuarioRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }

    // no funciona corregir: PROBLEMA DE SEGURIDAD - el passwordHash NO debe exponerse en respuestas GET, solo debe usarse en POST para crear/actualizar. Eliminar del DTO de respuesta o crear DTO separado para request/response.
    private UsuarioDTO toDTO(Usuario e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(e.getIdUsuario());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setNombre(e.getNombre());
        dto.setPasswordHash(e.getPasswordHash()); // PROBLEMA DE SEGURIDAD - exponiendo password hash
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
        e.setPasswordHash(dto.getPasswordHash());
        e.setIdRol(dto.getRolId());
        e.setPuntosTotales(dto.getPuntosTotales());
        e.setEstado(dto.getEstado());
        return e;
    }
}



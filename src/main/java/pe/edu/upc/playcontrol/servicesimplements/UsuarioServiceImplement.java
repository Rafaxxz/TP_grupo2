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

    // no funciona corregir: El método toDTO no está mapeando rolId ni puntosTotales que están en UsuarioDTO pero no en la entidad Usuario. Verificar si la entidad necesita estos campos o si el DTO está mal diseñado.
    // no funciona corregir: PROBLEMA DE SEGURIDAD - el passwordHash NO debe exponerse en respuestas GET, solo debe usarse en POST para crear/actualizar. Eliminar del DTO de respuesta o crear DTO separado para request/response.
    private UsuarioDTO toDTO(Usuario e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(e.getIdUsuario());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setNombre(e.getNombre());
        dto.setPasswordHash(e.getPasswordHash()); // PROBLEMA DE SEGURIDAD - exponiendo password hash
        dto.setEstado(e.getEstado());
        dto.setCreatedAt(e.getCreatedAt());
        // dto.setRolId() - no existe en entidad Usuario
        // dto.setPuntosTotales() - no existe en entidad Usuario
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

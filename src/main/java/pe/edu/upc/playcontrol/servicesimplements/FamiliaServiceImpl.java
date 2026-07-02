package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.UsuarioDTO;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IFamiliaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamiliaServiceImpl implements IFamiliaService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO vincularHijo(String padreUsername, String hijoEmail) {
        Usuario padre = usuarioRepository.findByUsername(padreUsername)
                .orElseThrow(() -> new IllegalArgumentException("Padre no encontrado"));

        Usuario hijo = usuarioRepository.findByEmail(hijoEmail)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el email: " + hijoEmail));

        if (hijo.getIdUsuario().equals(padre.getIdUsuario())) {
            throw new IllegalArgumentException("Un usuario no puede vincularse a sí mismo");
        }
        if (hijo.getPadreId() != null) {
            throw new IllegalStateException("Este usuario ya tiene un padre asignado");
        }

        hijo.setPadreId(padre.getIdUsuario());
        return toDTO(usuarioRepository.save(hijo));
    }

    @Override
    public List<UsuarioDTO> listarHijos(String padreUsername) {
        Usuario padre = usuarioRepository.findByUsername(padreUsername)
                .orElseThrow(() -> new IllegalArgumentException("Padre no encontrado"));

        return usuarioRepository.findByPadreId(padre.getIdUsuario())
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void desvincularHijo(String padreUsername, Integer hijoId) {
        Usuario padre = usuarioRepository.findByUsername(padreUsername)
                .orElseThrow(() -> new IllegalArgumentException("Padre no encontrado"));

        Usuario hijo = usuarioRepository.findById(hijoId)
                .orElseThrow(() -> new IllegalArgumentException("Hijo no encontrado con id: " + hijoId));

        if (!padre.getIdUsuario().equals(hijo.getPadreId())) {
            throw new IllegalArgumentException("Este usuario no está vinculado a tu cuenta");
        }

        hijo.setPadreId(null);
        usuarioRepository.save(hijo);
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
        dto.setPadreId(e.getPadreId());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}

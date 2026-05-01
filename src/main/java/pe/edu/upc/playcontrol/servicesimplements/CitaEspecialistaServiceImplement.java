package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.CitaEspecialistaDTO;
import pe.edu.upc.playcontrol.entities.CitaEspecialista;
import pe.edu.upc.playcontrol.entities.Especialista;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.ICitaEspecialistaRepository;
import pe.edu.upc.playcontrol.repositories.IEspecialistaRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ICitaEspecialistaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla cita_especialista
@Service
public class CitaEspecialistaServiceImplement implements ICitaEspecialistaService {

    @Autowired
    private ICitaEspecialistaRepository citaEspecialistaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IEspecialistaRepository especialistaRepository;

    @Override
    public List<CitaEspecialistaDTO> list() {
        return citaEspecialistaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CitaEspecialistaDTO insert(CitaEspecialistaDTO dto) {
        return toDTO(citaEspecialistaRepository.save(toEntity(dto)));
    }

    @Override
    public CitaEspecialistaDTO update(CitaEspecialistaDTO dto) {
        return toDTO(citaEspecialistaRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<CitaEspecialistaDTO> listId(UUID id) {
        return citaEspecialistaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(UUID id) {
        citaEspecialistaRepository.deleteById(id);
    }

    @Override
    public List<CitaEspecialistaDTO> listByUsuarioIdAndEstado(UUID usuarioId, String estado) {
        return citaEspecialistaRepository.findByUsuario_IdUsuarioAndEstado(usuarioId, estado).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CitaEspecialistaDTO> listByUsuarioId(UUID usuarioId) {
        return citaEspecialistaRepository.findByUsuario_IdUsuario(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CitaEspecialistaDTO> findProximasCitasPendientes(UUID usuarioId) {
        return citaEspecialistaRepository.findProximasCitasPendientes(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Object historialCitasPorEspecialista(UUID usuarioId) {
        return citaEspecialistaRepository.historialCitasPorEspecialista(usuarioId);
    }

    private CitaEspecialistaDTO toDTO(CitaEspecialista e) {
        CitaEspecialistaDTO dto = new CitaEspecialistaDTO();
        dto.setIdCita(e.getIdCita());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setEspecialistaId(e.getEspecialista() != null ? e.getEspecialista().getIdEspecialista() : null);
        dto.setFechaHora(e.getFechaHora());
        dto.setEstado(e.getEstado());
        return dto;
    }

    private CitaEspecialista toEntity(CitaEspecialistaDTO dto) {
        CitaEspecialista e = new CitaEspecialista();
        e.setIdCita(dto.getIdCita());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }
        if (dto.getEspecialistaId() != null) {
            Especialista especialista = especialistaRepository.findById(dto.getEspecialistaId()).orElse(null);
            e.setEspecialista(especialista);
        }
        e.setFechaHora(dto.getFechaHora());
        e.setEstado(dto.getEstado());
        return e;
    }
}

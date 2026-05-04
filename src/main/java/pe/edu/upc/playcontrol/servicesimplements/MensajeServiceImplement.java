package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.MensajeDTO;
import pe.edu.upc.playcontrol.entities.Mensaje;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IMensajeRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IMensajeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla mensaje
@Service
public class MensajeServiceImplement implements IMensajeService {

    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<MensajeDTO> list() {
        return mensajeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public MensajeDTO insert(MensajeDTO dto) {
        return toDTO(mensajeRepository.save(toEntity(dto)));
    }

    @Override
    public MensajeDTO update(MensajeDTO dto) {
        return toDTO(mensajeRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<MensajeDTO> listId(Integer id) {
        return mensajeRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(Integer id) {
        mensajeRepository.deleteById(id);
    }

    @Override
    public List<MensajeDTO> listByRemitenteId(Integer remitenteId) {
        return mensajeRepository.findByRemitente_IdUsuario(remitenteId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MensajeDTO> listNoLeidosByDestinatarioId(Integer destinatarioId) {
        return mensajeRepository.findByDestinatario_IdUsuarioAndLeido(destinatarioId, false).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MensajeDTO> findConversacionBetweenUsers(Integer usuarioA, Integer usuarioB) {
        return mensajeRepository.findConversacionBetweenUsers(usuarioA, usuarioB).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Object resumenNoLeidosPorRemitente(Integer usuarioId) {
        return mensajeRepository.resumenNoLeidosPorRemitente(usuarioId);
    }

    private MensajeDTO toDTO(Mensaje e) {
        MensajeDTO dto = new MensajeDTO();
        dto.setIdMensaje(e.getIdMensaje());
        dto.setRemitenteId(e.getRemitente() != null ? e.getRemitente().getIdUsuario() : null);
        dto.setDestinatarioId(e.getDestinatario() != null ? e.getDestinatario().getIdUsuario() : null);
        dto.setContenido(e.getContenido());
        dto.setContexto(e.getContexto());
        dto.setEnviadoEn(e.getEnviadoEn());
        dto.setLeido(e.getLeido());
        return dto;
    }

    private Mensaje toEntity(MensajeDTO dto) {
        Mensaje e = new Mensaje();
        e.setIdMensaje(dto.getIdMensaje());
        if (dto.getRemitenteId() != null) {
            Usuario remitente = usuarioRepository.findById(dto.getRemitenteId()).orElse(null);
            e.setRemitente(remitente);
        }
        if (dto.getDestinatarioId() != null) {
            Usuario destinatario = usuarioRepository.findById(dto.getDestinatarioId()).orElse(null);
            e.setDestinatario(destinatario);
        }
        e.setContenido(dto.getContenido());
        e.setContexto(dto.getContexto());
        e.setLeido(dto.getLeido());
        return e;
    }
}

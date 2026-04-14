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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImplement implements IMensajeService {

    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<MensajeDTO> getAll() {
        return mensajeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<MensajeDTO> getById(UUID id) {
        return mensajeRepository.findById(id).map(this::toDTO);
    }

    @Override
    public MensajeDTO save(MensajeDTO dto) {
        return toDTO(mensajeRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        mensajeRepository.deleteById(id);
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

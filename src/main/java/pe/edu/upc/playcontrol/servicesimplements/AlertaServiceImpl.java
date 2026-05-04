package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.AlertaRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlertaServiceImpl implements AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private SesionJuegoRepository sesionJuegoRepository;

    @Override
    public List<AlertaDTO> getAll() {
        return alertaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AlertaDTO> getById(UUID id) {
        return alertaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public AlertaDTO save(AlertaDTO dto) {
        return toDTO(alertaRepository.save(toEntity(dto)));
    }

    @Override
<<<<<<< HEAD
    public Alerta buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
=======
    public void delete(UUID id) {
        alertaRepository.deleteById(id);
    }

    private AlertaDTO toDTO(Alerta e) {
        AlertaDTO dto = new AlertaDTO();
        dto.setIdAlerta(e.getIdAlerta());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setSesionId(e.getSesionJuego() != null ? e.getSesionJuego().getIdSesion() : null);
        dto.setTipo(e.getTipo());
        dto.setMensaje(e.getMensaje());
        dto.setNivel(e.getNivel());
        dto.setEmitidaEn(e.getEmitidaEn());
        dto.setLeida(e.getLeida());
        return dto;
    }

    private Alerta toEntity(AlertaDTO dto) {
        Alerta e = new Alerta();
        e.setIdAlerta(dto.getIdAlerta());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }

        if (dto.getSesionId() != null) {
            SesionJuego sesion = sesionJuegoRepository.findById(dto.getSesionId()).orElse(null);
            e.setSesionJuego(sesion);
        }

        e.setTipo(dto.getTipo());
        e.setMensaje(dto.getMensaje());
        e.setNivel(dto.getNivel());
        e.setEmitidaEn(dto.getEmitidaEn());
        e.setLeida(dto.getLeida());

        return e;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
    }

    @Override
    public List<AlertaDTO> buscarPorUsuario(UUID usuarioId) {
        return alertaRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertaDTO> obtenerNoLeidas() {
        return alertaRepository.findByLeidaFalse()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


}

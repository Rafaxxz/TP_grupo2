package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.repositories.IAlertaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaServiceImpl implements AlertaService {

    @Autowired
    private IAlertaRepository alertaRepository;

    @Override
    public Alerta guardar(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    @Override
    public List<Alerta> listar() {
        return alertaRepository.findAll();
    }

    @Override
    public Alerta buscarPorId(Integer id) {
        return alertaRepository.findById(id).orElse(null);
    }

    @Override
    public List<AlertaDTO> buscarPorUsuario(Integer usuarioId) {
        return alertaRepository.findByUsuario_IdUsuario(usuarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<AlertaDTO> obtenerNoLeidas() {
        return alertaRepository.findByLeidaFalse()
                .stream().map(this::toDTO).collect(Collectors.toList());
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
}

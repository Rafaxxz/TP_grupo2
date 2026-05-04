package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SesionJuegoServiceImpl implements SesionJuegoService {

    @Autowired
    private SesionJuegoRepository sesionJuegoRepository;

    @Override
    public SesionJuego guardar(SesionJuego sesionJuego) {
        return sesionJuegoRepository.save(sesionJuego);
    }

    @Override
    public List<SesionJuego> listar() {
        return sesionJuegoRepository.findAll();
    }

    @Override
    public SesionJuego buscarPorId(Integer id) {
        return sesionJuegoRepository.findById(id).orElse(null);
    }

    @Override
    public List<SesionJuego> historialPorUsuario(Integer usuarioId) {
        return sesionJuegoRepository.findByUsuario_IdUsuario(usuarioId);
    }

    @Override
    public List<SesionJuegoDTO> buscarPorUsuario(Integer usuarioId) {
        return sesionJuegoRepository.findByUsuario_IdUsuario(usuarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SesionJuegoDTO> buscarPorFecha(LocalDate fecha) {
        return sesionJuegoRepository.findByFecha(fecha)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SesionJuegoDTO toDTO(SesionJuego e) {
        SesionJuegoDTO dto = new SesionJuegoDTO();
        dto.setIdSesion(e.getIdSesion());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setJuegoId(e.getJuego() != null ? e.getJuego().getIdJuego() : null);
        dto.setInicio(e.getInicio());
        dto.setFin(e.getFin());
        dto.setDuracionMinutos(e.getDuracionMinutos());
        dto.setFecha(e.getFecha());
        return dto;
    }
}

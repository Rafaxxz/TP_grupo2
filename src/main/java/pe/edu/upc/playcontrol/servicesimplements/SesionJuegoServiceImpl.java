package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SesionJuegoServiceImpl implements SesionJuegoService {

    @Autowired
    private SesionJuegoRepository sesionJuegoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public List<SesionJuegoDTO> getAll() {
        return sesionJuegoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<SesionJuegoDTO> getById(UUID id) {
        return sesionJuegoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public SesionJuegoDTO save(SesionJuegoDTO dto) {
        return toDTO(sesionJuegoRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(UUID id) {
        sesionJuegoRepository.deleteById(id);
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

    private SesionJuego toEntity(SesionJuegoDTO dto) {
        SesionJuego e = new SesionJuego();
        e.setIdSesion(dto.getIdSesion());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            e.setUsuario(usuario);
        }

        if (dto.getJuegoId() != null) {
            Juego juego = juegoRepository.findById(dto.getJuegoId()).orElse(null);
            e.setJuego(juego);
        }

        e.setInicio(dto.getInicio());
        e.setFin(dto.getFin());
        e.setFecha(dto.getFecha());

        return e;
    }
}
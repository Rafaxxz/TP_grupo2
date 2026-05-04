package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
<<<<<<< HEAD
=======
import pe.edu.upc.playcontrol.entities.Juego;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.time.LocalDate;
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
<<<<<<< HEAD
    public SesionJuego buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
=======
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
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
    }

    @Override
    public List<SesionJuego> historialPorUsuario(Integer usuarioId) {
        return repository.historialPorUsuario(usuarioId);
    }

    @Override
    public List<SesionJuegoDTO> buscarPorUsuario(UUID usuarioId) {
        return sesionJuegoRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SesionJuegoDTO> buscarPorFecha(LocalDate fecha) {
        return sesionJuegoRepository.findByFecha(fecha)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.SesionJuegoDTO;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SesionJuegoServiceImpl implements SesionJuegoService {

    private final SesionJuegoRepository repository;

    public SesionJuegoServiceImpl(SesionJuegoRepository repository) {
        this.repository = repository;
    }

    @Override
    public SesionJuego guardar(SesionJuego sesionJuego) {
        return repository.save(sesionJuego);
    }

    @Override
    public List<SesionJuego> listar() {
        return repository.findAll();
    }

    @Override
    public SesionJuego buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
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

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.SesionJuego;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.SesionJuegoService;

import java.util.List;
import java.util.UUID;

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
    public SesionJuego buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
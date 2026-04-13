package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.UUID;

@Service
public class JuegoServiceImpl implements JuegoService {

    private final JuegoRepository repository;

    public JuegoServiceImpl(JuegoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Juego guardar(Juego juego) {
        return repository.save(juego);
    }

    @Override
    public List<Juego> listar() {
        return repository.findAll();
    }

    @Override
    public Juego buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
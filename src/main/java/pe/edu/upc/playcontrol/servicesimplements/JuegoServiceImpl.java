package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired

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
    public Juego buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<JuegoDTO> buscarPorPlataforma(String plataforma) {
        return juegoRepository.findByPlataformaIgnoreCase(plataforma)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JuegoDTO> buscarPorCategoria(Integer idCategoria) {
        return juegoRepository.findByCategoriaJuego_IdCategoria(idCategoria)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

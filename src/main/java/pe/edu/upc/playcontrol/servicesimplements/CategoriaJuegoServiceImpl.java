package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.repositories.CategoriaJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.List;

@Service
public class CategoriaJuegoServiceImpl implements CategoriaJuegoService {

    private final CategoriaJuegoRepository repository;

    public CategoriaJuegoServiceImpl(CategoriaJuegoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaJuego guardar(CategoriaJuego categoriaJuego) {
        return repository.save(categoriaJuego);
    }

    @Override
    public List<CategoriaJuego> listar() {
        return repository.findAll();
    }
}
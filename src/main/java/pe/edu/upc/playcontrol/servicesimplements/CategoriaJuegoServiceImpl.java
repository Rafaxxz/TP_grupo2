package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.repositories.CategoriaJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaJuegoServiceImpl implements CategoriaJuegoService {

    @Autowired
    private CategoriaJuegoService categoriaJuegoRepository;
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

    @Override
    public List<CategoriaJuegoDTO> getAll() {
        return List.of();
    }

    @Override
    public Optional<CategoriaJuegoDTO> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public CategoriaJuegoDTO save(CategoriaJuegoDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<CategoriaJuegoDTO> buscarPorNombre(String nombre) {
        return categoriaJuegoRepository.buscarPorNombre(nombre)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return categoriaJuegoRepository.existePorNombre(nombre);
    }
}
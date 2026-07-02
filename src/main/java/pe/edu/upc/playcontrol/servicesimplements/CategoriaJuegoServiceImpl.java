package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.CategoriaJuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.repositories.ICategoriaJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.CategoriaJuegoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaJuegoServiceImpl implements CategoriaJuegoService {

    @Autowired
    private ICategoriaJuegoRepository categoriaJuegoRepository;

    @Override
    public List<CategoriaJuegoDTO> getAll() {
        return categoriaJuegoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaJuegoDTO> getById(Integer id) {
        return categoriaJuegoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public CategoriaJuegoDTO save(CategoriaJuegoDTO dto) {
        return toDTO(categoriaJuegoRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(Integer id) {
        categoriaJuegoRepository.deleteById(id);
    }

    @Override
    public List<CategoriaJuegoDTO> buscarPorNombre(String nombre) {
        return categoriaJuegoRepository.buscarPorNombre(nombre)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return categoriaJuegoRepository.existePorNombre(nombre);
    }

    private CategoriaJuegoDTO toDTO(CategoriaJuego e) {
        CategoriaJuegoDTO dto = new CategoriaJuegoDTO();
        dto.setIdCategoria(e.getIdCategoria());
        dto.setNombre(e.getNombre());
        return dto;
    }

    private CategoriaJuego toEntity(CategoriaJuegoDTO dto) {
        CategoriaJuego e = new CategoriaJuego();
        e.setIdCategoria(dto.getIdCategoria());
        e.setNombre(dto.getNombre());
        return e;
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.repositories.ICategoriaJuegoRepository;
import pe.edu.upc.playcontrol.repositories.IJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired
    private IJuegoRepository juegoRepository;

    @Autowired
    private ICategoriaJuegoRepository categoriaJuegoRepository;

    @Override
    public Juego guardar(Juego juego) {
        return juegoRepository.save(juego);
    }

    @Override
    public List<Juego> listar() {
        return juegoRepository.findAll();
    }

    @Override
    public Juego buscarPorId(Integer id) {
        return juegoRepository.findById(id).orElse(null);
    }

    @Override
    public List<JuegoDTO> buscarPorPlataforma(String plataforma) {
        return juegoRepository.findByPlataformaIgnoreCase(plataforma)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<JuegoDTO> buscarPorCategoria(Integer idCategoria) {
        return juegoRepository.findByCategoriaJuego_IdCategoria(idCategoria)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private JuegoDTO toDTO(Juego e) {
        JuegoDTO dto = new JuegoDTO();
        dto.setIdJuego(e.getIdJuego());
        dto.setNombre(e.getNombre());
        dto.setPlataforma(e.getPlataforma());
        dto.setCategoriaId(e.getCategoriaJuego() != null ? e.getCategoriaJuego().getIdCategoria() : null);
        return dto;
    }
}

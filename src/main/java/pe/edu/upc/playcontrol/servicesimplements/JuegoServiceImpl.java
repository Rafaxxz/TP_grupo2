package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
<<<<<<< HEAD
=======
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.repositories.CategoriaJuegoRepository;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
import java.util.UUID;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import java.util.stream.Collectors;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired
<<<<<<< HEAD

    private final JuegoRepository repository;
=======
    private JuegoRepository juegoRepository;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

    @Autowired
    private CategoriaJuegoRepository categoriaJuegoRepository;

    @Override
    public List<JuegoDTO> getAll() {
        return juegoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<JuegoDTO> getById(UUID id) {
        return juegoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public JuegoDTO save(JuegoDTO dto) {
        return toDTO(juegoRepository.save(toEntity(dto)));
    }

    @Override
<<<<<<< HEAD
    public Juego buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
=======
    public void delete(UUID id) {
        juegoRepository.deleteById(id);
    }

    private JuegoDTO toDTO(Juego e) {
        JuegoDTO dto = new JuegoDTO();
        dto.setIdJuego(e.getIdJuego());
        dto.setNombre(e.getNombre());
        dto.setPlataforma(e.getPlataforma());
        dto.setCategoriaId(e.getCategoriaJuego() != null ? e.getCategoriaJuego().getIdCategoria() : null);
        return dto;
    }

    private Juego toEntity(JuegoDTO dto) {
        Juego e = new Juego();
        e.setIdJuego(dto.getIdJuego());
        e.setNombre(dto.getNombre());
        e.setPlataforma(dto.getPlataforma());

        if (dto.getCategoriaId() != null) {
            CategoriaJuego categoria = categoriaJuegoRepository.findById(dto.getCategoriaId()).orElse(null);
            e.setCategoriaJuego(categoria);
        }

        return e;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
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

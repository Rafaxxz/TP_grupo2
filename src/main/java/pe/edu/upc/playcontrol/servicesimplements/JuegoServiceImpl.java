package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.JuegoDTO;
import pe.edu.upc.playcontrol.entities.CategoriaJuego;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.repositories.CategoriaJuegoRepository;
import pe.edu.upc.playcontrol.repositories.JuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.JuegoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

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
    }
}
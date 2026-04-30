package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RecompensaDTO;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRecompensaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensaServiceImplement implements IRecompensaService {

    //1. inyectar el repositorio
    @Autowired
    private IRecompensaRepository recompensaRepository;

    // 2. traer lo de la base de datos y convertirlo a un dto
    @Override
    public List<RecompensaDTO> getAll() {
        return recompensaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 3. traer un dto por id
    @Override
    public RecompensaDTO getById(Integer id) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + id));
        return toDto(recompensa);
    }

    // 4. guardar un dto
    @Override
    public RecompensaDTO save(RecompensaDTO dto) {
        return toDto(recompensaRepository.save(toEntity(dto)));
    }

    // 5. actualizar un dto
    @Override
    public RecompensaDTO update(Integer id, RecompensaDTO dto) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + id));
        recompensa.setNombre(dto.getNombre());
        recompensa.setDescripcion(dto.getDescripcion());
        recompensa.setTipo(dto.getTipo());
        recompensa.setCostoPuntos(dto.getCostoPuntos());
        recompensa.setRecursoUrl(dto.getRecursoUrl());
        return toDto(recompensaRepository.save(recompensa));
    }

    // 6. eliminar un dto por id
    @Override
    public void delete(Integer id) {
        recompensaRepository.deleteById(id);
    }

    // 7. convierte la entidad a un DTO linea por linea (para el cliente)
    private RecompensaDTO toDto(Recompensa recompensa) {
        RecompensaDTO dto = new RecompensaDTO();
        dto.setIdRecompensa(recompensa.getIdRecompensa());
        dto.setNombre(recompensa.getNombre());
        dto.setDescripcion(recompensa.getDescripcion());
        dto.setTipo(recompensa.getTipo());
        dto.setCostoPuntos(recompensa.getCostoPuntos());
        dto.setRecursoUrl(recompensa.getRecursoUrl());
        return dto;
    }

    // 8. convierte el DTO a una entidad (para guardar en la base de datos)
    private Recompensa toEntity(RecompensaDTO dto) {
        Recompensa recompensa = new Recompensa();
        recompensa.setNombre(dto.getNombre());
        recompensa.setDescripcion(dto.getDescripcion());
        recompensa.setTipo(dto.getTipo());
        recompensa.setCostoPuntos(dto.getCostoPuntos());
        recompensa.setRecursoUrl(dto.getRecursoUrl());
        return recompensa;
    }
}

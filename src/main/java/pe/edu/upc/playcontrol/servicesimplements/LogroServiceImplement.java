package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogroServiceImplement implements ILogroService {

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<LogroDTO> getAll() {
        return logroRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogroDTO getById(Integer id) {
        Logro logro = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + id));
        return toDto(logro);
    }

    @Override
    public LogroDTO save(LogroDTO dto) {
        return toDto(logroRepository.save(toEntity(dto)));
    }

    @Override
    public LogroDTO update(Integer id, LogroDTO dto) {
        // Primero verificamos que el logro existe, luego actualizamos campo a campo.
        Logro logro = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + id));
        logro.setNombre(dto.getNombre());
        logro.setDescripcion(dto.getDescripcion());
        logro.setIconoUrl(dto.getIconoUrl());
        logro.setPuntosOtorgados(dto.getPuntosOtorgados());
        logro.setCriterio(dto.getCriterio());
        logro.setValorCriterio(dto.getValorCriterio());
        return toDto(logroRepository.save(logro));
    }

    @Override
    public void delete(Integer id) {
        logroRepository.deleteById(id);
    }

    // convierte la entidad a un DTO linea por linea
    private LogroDTO toDto(Logro logro) {
        LogroDTO dto = new LogroDTO();
        dto.setIdLogro(logro.getIdLogro());
        dto.setNombre(logro.getNombre());
        dto.setDescripcion(logro.getDescripcion());
        dto.setIconoUrl(logro.getIconoUrl());
        dto.setPuntosOtorgados(logro.getPuntosOtorgados());
        dto.setCriterio(logro.getCriterio());
        dto.setValorCriterio(logro.getValorCriterio());
        return dto;
    }

    // convierte el DTO a una entidad
    private Logro toEntity(LogroDTO dto) {
        Logro logro = new Logro();
        logro.setNombre(dto.getNombre());
        logro.setDescripcion(dto.getDescripcion());
        logro.setIconoUrl(dto.getIconoUrl());
        logro.setPuntosOtorgados(dto.getPuntosOtorgados());
        logro.setCriterio(dto.getCriterio());
        logro.setValorCriterio(dto.getValorCriterio());
        return logro;
    }
}

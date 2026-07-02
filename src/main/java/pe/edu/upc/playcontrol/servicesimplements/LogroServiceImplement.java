package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla logro
@Service
public class LogroServiceImplement implements ILogroService {

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<LogroDTO> list() {
        return logroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public LogroDTO insert(LogroDTO dto) {
        return toDTO(logroRepository.save(toEntity(dto)));
    }

    @Override
    public LogroDTO update(LogroDTO dto) {
        return toDTO(logroRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<LogroDTO> listId(Integer id) {
        return logroRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(Integer id) {
        logroRepository.deleteById(id);
    }

    @Override
    public List<LogroDTO> listByCriterio(String criterio) {
        return logroRepository.findByCriterio(criterio).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LogroDTO> listByCriterioOrdenado(String criterio) {
        return logroRepository.findByCriterioOrdenado(criterio).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Object findConEstadisticasDesbloqueos() {
        return logroRepository.findConEstadisticasDesbloqueos();
    }

    private LogroDTO toDTO(Logro e) {
        LogroDTO dto = new LogroDTO();
        dto.setIdLogro(e.getIdLogro());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setIconoUrl(e.getIconoUrl());
        dto.setPuntosOtorgados(e.getPuntosOtorgados());
        dto.setCriterio(e.getCriterio());
        dto.setValorCriterio(e.getValorCriterio());
        return dto;
    }

    private Logro toEntity(LogroDTO dto) {
        Logro e = new Logro();
        e.setIdLogro(dto.getIdLogro());
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setIconoUrl(dto.getIconoUrl());
        e.setPuntosOtorgados(dto.getPuntosOtorgados());
        e.setCriterio(dto.getCriterio());
        e.setValorCriterio(dto.getValorCriterio());
        return e;
    }
}

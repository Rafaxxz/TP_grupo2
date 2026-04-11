package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LogroDTO;
import pe.edu.upc.playcontrol.entities.Logro;
import pe.edu.upc.playcontrol.repositories.ILogroRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ILogroService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// @Service le dice a Spring que este es un bean de lógica de negocio.
// Spring lo detecta en el classpath y lo registra para poder inyectarlo
// en los controladores u otros componentes con @Autowired.
@Service
public class LogroServiceImplement implements ILogroService {

    // @Autowired inyecta el repositorio automáticamente.
    // Spring detecta que ILogroRepository es un bean (@Repository) y lo conecta aquí
    // sin que lo instancies con "new". Esto es Inversión de Control (IoC).
    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<LogroDTO> getAll() {
        // findAll() trae todos los registros de la tabla logro.
        // .stream() + .map(this::toDto) convierte cada Logro a LogroDTO.
        // .collect(Collectors.toList()) materializa el stream en una lista.
        return logroRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogroDTO getById(UUID id) {
        // findById devuelve Optional<Logro>.
        // orElseThrow lanza RuntimeException si no existe, que Spring convierte en HTTP 500.
        // (En proyectos más avanzados se usaría una excepción personalizada para HTTP 404.)
        Logro logro = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + id));
        return toDto(logro);
    }

    @Override
    public LogroDTO save(LogroDTO dto) {
        // Convertimos el DTO a entidad, persistimos, y devolvemos el DTO del objeto guardado.
        // El ID se genera automáticamente en @PrePersist / @GeneratedValue.
        return toDto(logroRepository.save(toEntity(dto)));
    }

    @Override
    public LogroDTO update(UUID id, LogroDTO dto) {
        // Primero verificamos que el logro existe, luego actualizamos campo a campo.
        // Usamos el objeto existente para conservar el ID y no crear uno nuevo.
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
    public void delete(UUID id) {
        logroRepository.deleteById(id);
    }

    // Métodos privados de mapeo: convierten Entidad ↔ DTO.
    // Al centralizar el mapeo aquí, ni la entidad ni el DTO saben el uno del otro.

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

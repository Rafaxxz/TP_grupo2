package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.CanjeRecompensaDTO;
import pe.edu.upc.playcontrol.entities.CanjeRecompensa;
import pe.edu.upc.playcontrol.entities.Recompensa;
import pe.edu.upc.playcontrol.repositories.ICanjeRecompensaRepository;
import pe.edu.upc.playcontrol.repositories.IRecompensaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ICanjeRecompensaService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CanjeRecompensaServiceImplement implements ICanjeRecompensaService {

    @Autowired
    private ICanjeRecompensaRepository canjeRecompensaRepository;

    @Autowired
    private IRecompensaRepository recompensaRepository;

    @Override
    public List<CanjeRecompensaDTO> getAll() {
        return canjeRecompensaRepository.findAll()
                // convierte la lista para procesarla
                .stream()
                // convierte cada CanjeRecompensa a CanjeRecompensaDTO
                .map(this::toDto)
                // convierte el stream en una lista
                .collect(Collectors.toList());
    }

    @Override
    public CanjeRecompensaDTO getById(UUID id) {
        CanjeRecompensa canje = canjeRecompensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CanjeRecompensa no encontrado con id: " + id));
        return toDto(canje);
    }

    @Override
    public List<CanjeRecompensaDTO> getByUsuarioId(UUID usuarioId) {
        return canjeRecompensaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // no funciona corregir: No valida que el usuarioId exista antes de guardar. Debería inyectar IUsuarioRepository y verificar con usuarioRepository.existsById(dto.getUsuarioId()) o lanzar excepción si no existe.
    // no funciona corregir: No valida que el usuario tenga suficientes puntos para canjear la recompensa (recompensa.getCostoPuntos() vs puntos del usuario).
    // aqui le asignamos los datos del dto
    @Override
    public CanjeRecompensaDTO save(CanjeRecompensaDTO dto) {
        CanjeRecompensa canje = new CanjeRecompensa();
        canje.setUsuarioId(dto.getUsuarioId()); // No valida que el usuario exista
        // valida antes de pasarlo que la recompensa exista
        Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId())
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con id: " + dto.getRecompensaId()));
        canje.setRecompensa(recompensa);
        canje.setPuntosUsados(dto.getPuntosUsados());
        return toDto(canjeRecompensaRepository.save(canje));
    }

    @Override
    public void delete(UUID id) {
        canjeRecompensaRepository.deleteById(id);
    }

    // convierte la entidad a un DTO linea por linea
    private CanjeRecompensaDTO toDto(CanjeRecompensa canje) {
        CanjeRecompensaDTO dto = new CanjeRecompensaDTO();
        dto.setIdCanje(canje.getIdCanje());
        dto.setUsuarioId(canje.getUsuarioId());
        // verifica que la recompensa no sea nula antes de obtener su id
        dto.setRecompensaId(canje.getRecompensa() != null ? canje.getRecompensa().getIdRecompensa() : null);
        dto.setPuntosUsados(canje.getPuntosUsados());
        dto.setCanjeadoEn(canje.getCanjeadoEn());
        return dto;
    }
}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.repositories.AlertaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository repository;

    public AlertaServiceImpl(AlertaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Alerta guardar(Alerta alerta) {
        return repository.save(alerta);
    }

    @Override
    public List<Alerta> listar() {
        return repository.findAll();
    }

    @Override
    public Alerta buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<AlertaDTO> buscarPorUsuario(UUID usuarioId) {
        return alertaRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertaDTO> obtenerNoLeidas() {
        return alertaRepository.findByLeidaFalse()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


}

package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.Alerta;
import pe.edu.upc.playcontrol.repositories.AlertaRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.AlertaService;

import java.util.List;

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
}

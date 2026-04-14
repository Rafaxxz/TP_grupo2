package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.List;
import java.util.UUID;

@Service
public class LimiteTiempoServiceImplement implements LimiteTiempoService {

    @Autowired
    private LimiteTiempoRepository limiteTiempoRepository;

    @Override
    public LimiteTiempo guardar(LimiteTiempo limiteTiempo) {
        return limiteTiempoRepository.save(limiteTiempo);
    }

    @Override
    public List<LimiteTiempo> listar() {
        return limiteTiempoRepository.findAll();
    }

    @Override
    public LimiteTiempo buscarPorId(UUID id) {
        return limiteTiempoRepository.findById(id).orElse(null);
    }
}




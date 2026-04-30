package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
import pe.edu.upc.playcontrol.repositories.LimiteTiempoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.LimiteTiempoService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public LimiteTiempo buscarPorId(Integer id) {
        return limiteTiempoRepository.findById(id).orElse(null);
    }

    @Override
    public List<LimiteTiempoDTO> buscarPorUsuario(UUID usuarioId) {
        return limiteTiempoRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LimiteTiempoDTO> obtenerBloqueados() {
        return limiteTiempoRepository.findByBloqueoActivoTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

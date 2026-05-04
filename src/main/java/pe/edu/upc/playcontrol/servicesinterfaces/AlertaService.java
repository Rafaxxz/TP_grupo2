package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.AlertaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlertaService {
<<<<<<< HEAD
    Alerta guardar(Alerta alerta);
    List<Alerta> listar();
    Alerta buscarPorId(Integer id);
    List<AlertaDTO> buscarPorUsuario(UUID usuarioId);

    List<AlertaDTO> obtenerNoLeidas();
}
=======
    List<AlertaDTO> getAll();
    Optional<AlertaDTO> getById(UUID id);
    AlertaDTO save(AlertaDTO dto);
    void delete(UUID id);
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

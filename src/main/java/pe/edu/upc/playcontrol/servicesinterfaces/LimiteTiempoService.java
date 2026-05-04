package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;
<<<<<<< HEAD
import pe.edu.upc.playcontrol.entities.LimiteTiempo;
=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LimiteTiempoService {
<<<<<<< HEAD
    LimiteTiempo guardar(LimiteTiempo limiteTiempo);
    List<LimiteTiempo> listar();
    LimiteTiempo buscarPorId(Integer id);
    List<LimiteTiempoDTO> buscarPorUsuario(UUID usuarioId);

    List<LimiteTiempoDTO> obtenerBloqueados();
}
=======
    List<LimiteTiempoDTO> getAll();
    Optional<LimiteTiempoDTO> getById(UUID id);
    LimiteTiempoDTO save(LimiteTiempoDTO dto);
    void delete(UUID id);
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

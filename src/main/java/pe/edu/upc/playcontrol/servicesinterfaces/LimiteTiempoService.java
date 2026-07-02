package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LimiteTiempoDTO;
import pe.edu.upc.playcontrol.entities.LimiteTiempo;

import java.util.List;

public interface LimiteTiempoService {
    LimiteTiempo guardar(LimiteTiempo limiteTiempo);
    List<LimiteTiempo> listar();
    LimiteTiempo buscarPorId(Integer id);
    List<LimiteTiempoDTO> buscarPorUsuario(Integer usuarioId);
    List<LimiteTiempoDTO> obtenerBloqueados();
}

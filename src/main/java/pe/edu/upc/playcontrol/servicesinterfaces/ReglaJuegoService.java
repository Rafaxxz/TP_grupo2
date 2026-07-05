package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.ReglaJuegoDTO;
import pe.edu.upc.playcontrol.dtos.ReglaJuegoRequest;

import java.util.List;

public interface ReglaJuegoService {
    ReglaJuegoDTO crear(ReglaJuegoRequest req);
    ReglaJuegoDTO actualizar(Integer id, ReglaJuegoRequest req);
    void eliminar(Integer id);
    List<ReglaJuegoDTO> listarPorHijo(Integer hijoId);
}

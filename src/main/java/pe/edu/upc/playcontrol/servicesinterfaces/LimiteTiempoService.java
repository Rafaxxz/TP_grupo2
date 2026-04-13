package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.LimiteTiempo;

import java.util.List;
import java.util.UUID;

public interface LimiteTiempoService {
    LimiteTiempo guardar(LimiteTiempo limiteTiempo);
    List<LimiteTiempo> listar();
    LimiteTiempo buscarPorId(UUID id);
}
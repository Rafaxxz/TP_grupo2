package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.entities.Alerta;

import java.util.List;
import java.util.UUID;

public interface AlertaService {
    Alerta guardar(Alerta alerta);
    List<Alerta> listar();
    Alerta buscarPorId(UUID id);
}
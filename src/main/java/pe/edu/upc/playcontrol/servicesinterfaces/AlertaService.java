package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.AlertaDTO;
import pe.edu.upc.playcontrol.entities.Alerta;

import java.util.List;

public interface AlertaService {
    Alerta guardar(Alerta alerta);
    List<Alerta> listar();
    Alerta buscarPorId(Integer id);
    List<AlertaDTO> buscarPorUsuario(Integer usuarioId);
    List<AlertaDTO> obtenerNoLeidas();
}

package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.UsuarioDTO;

import java.util.List;

public interface IFamiliaService {
    UsuarioDTO vincularHijo(String padreUsername, String hijoEmail);
    List<UsuarioDTO> listarHijos(String padreUsername);
    void desvincularHijo(String padreUsername, Integer hijoId);
}

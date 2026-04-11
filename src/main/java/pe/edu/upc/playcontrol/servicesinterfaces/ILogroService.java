package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.LogroDTO;

import java.util.List;
import java.util.UUID;

// La interfaz define el CONTRATO de la lógica de negocio.
// El controlador depende de esta interfaz, nunca de la implementación concreta.
// Esto aplica el principio de inversión de dependencias (D de SOLID):
// si mañana cambias la implementación (otro ORM, otra BD), el controlador no se toca.
public interface ILogroService {

    List<LogroDTO> getAll();

    LogroDTO getById(UUID id);

    LogroDTO save(LogroDTO dto);

    LogroDTO update(UUID id, LogroDTO dto);

    void delete(UUID id);
}

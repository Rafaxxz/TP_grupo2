package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.MensajeDTO;

import java.util.List;
import java.util.Optional;

public interface IMensajeService {
    List<MensajeDTO> list();
    MensajeDTO insert(MensajeDTO dto);
    MensajeDTO update(MensajeDTO dto);
    Optional<MensajeDTO> listId(Integer id);
    void delete(Integer id);

    // Filtro simple: mensajes enviados por un remitente
    List<MensajeDTO> listByRemitenteId(Integer remitenteId);

    // Query de decisión: mensajes no leídos recibidos por un usuario
    List<MensajeDTO> listNoLeidosByDestinatarioId(Integer destinatarioId);

    // Query 1: Conversación completa entre dos usuarios
    List<MensajeDTO> findConversacionBetweenUsers(Integer usuarioA, Integer usuarioB);

    // Query 2: Resumen de mensajes no leídos por persona que los envió
    Object resumenNoLeidosPorRemitente(Integer usuarioId);
}

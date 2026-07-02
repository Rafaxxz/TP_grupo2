package pe.edu.upc.playcontrol.servicesinterfaces;

import pe.edu.upc.playcontrol.dtos.ChatHistorialDTO;
import pe.edu.upc.playcontrol.dtos.ChatMensajeDTO;
import pe.edu.upc.playcontrol.dtos.ChatRespuestaDTO;

import java.util.List;

public interface IChatbotService {
    ChatRespuestaDTO preguntar(ChatMensajeDTO request);
    List<ChatHistorialDTO> historial(Integer idUsuario);
}

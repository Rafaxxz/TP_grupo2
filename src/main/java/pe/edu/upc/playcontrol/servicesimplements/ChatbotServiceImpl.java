package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.ChatHistorialDTO;
import pe.edu.upc.playcontrol.dtos.ChatMensajeDTO;
import pe.edu.upc.playcontrol.dtos.ChatRespuestaDTO;
import pe.edu.upc.playcontrol.entities.ChatHistorial;
import pe.edu.upc.playcontrol.entities.Rol;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IChatHistorialRepository;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IChatbotService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatbotServiceImpl implements IChatbotService {

    @Autowired
    private IChatHistorialRepository chatRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private GeminiClient geminiClient;

    @Override
    public ChatRespuestaDTO preguntar(ChatMensajeDTO request) {
        if (request == null || request.getIdUsuario() == null) {
            throw new IllegalArgumentException("El idUsuario es obligatorio");
        }
        if (request.getMensaje() == null || request.getMensaje().isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con id: " + request.getIdUsuario()));

        String rolNombre = resolverRol(usuario);

        // Contexto conversacional: últimas 6 interacciones en orden cronológico
        List<ChatHistorial> ultimas =
                chatRepository.findTop6ByUsuario_IdUsuarioOrderByCreadoEnDesc(usuario.getIdUsuario());
        Collections.reverse(ultimas);
        List<GeminiClient.Turno> turnos = ultimas.stream()
                .map(c -> new GeminiClient.Turno(c.getPregunta(), c.getRespuesta()))
                .collect(Collectors.toList());

        String systemPrompt = construirSystemPrompt(rolNombre, usuario);

        // Puede lanzar GeminiRateLimitException (429/503) -> la maneja el controller
        String respuesta = geminiClient.generar(systemPrompt, turnos, request.getMensaje());

        // Persistir la interacción
        ChatHistorial registro = new ChatHistorial();
        registro.setUsuario(usuario);
        registro.setPregunta(request.getMensaje());
        registro.setRespuesta(respuesta);
        registro.setRolUsuario(rolNombre);
        registro = chatRepository.save(registro);

        ChatRespuestaDTO dto = new ChatRespuestaDTO();
        dto.setIdChat(registro.getIdChat());
        dto.setPregunta(registro.getPregunta());
        dto.setRespuesta(registro.getRespuesta());
        dto.setRolUsuario(registro.getRolUsuario());
        dto.setCreadoEn(registro.getCreadoEn());
        return dto;
    }

    @Override
    public List<ChatHistorialDTO> historial(Integer idUsuario) {
        return chatRepository.findByUsuario_IdUsuarioOrderByCreadoEnDesc(idUsuario)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String resolverRol(Usuario usuario) {
        if (usuario.getIdRol() == null) return "HIJO";
        return rolRepository.findById(usuario.getIdRol())
                .map(Rol::getNombre)
                .orElse("HIJO");
    }

    // Prompt de sistema distinto según el rol del usuario
    private String construirSystemPrompt(String rol, Usuario usuario) {
        if ("PADRE".equalsIgnoreCase(rol)) {
            return """
                Eres PlayBot, el asistente de PlayControl, una app de control parental.
                Estás hablando con un PADRE o MADRE. Usa un tono profesional, empático y claro.
                Ayúdalo con: cómo configurar límites de tiempo de juego de sus hijos,
                interpretar alertas, entender reportes de actividad, y buenas prácticas de
                crianza digital. Da respuestas concretas y accionables. Responde en español,
                de forma breve (máximo 4-5 oraciones salvo que pidan más detalle).
                No inventes datos específicos de la cuenta que no conozcas; si necesitas
                información puntual, sugiere dónde encontrarla dentro de la app.
                """;
        }
        // HIJO (o cualquier otro rol) -> tono amigable / gamificado
        int puntos = usuario.getPuntosTotales() != null ? usuario.getPuntosTotales() : 0;
        return """
            Eres PlayBot, el asistente amigable de PlayControl para chicos y chicas.
            Estás hablando con un jugador (rol HIJO). Usa un tono cercano, motivador y
            divertido, con alguna emoción tipo emoji ocasional. Ayúdalo con: sus juegos,
            retos disponibles, cómo ganar más puntos, y cómo canjear recompensas.
            Actualmente tiene %d puntos, tenlo presente para motivarlo.
            Responde en español, breve y fácil de entender. Fomenta el buen uso del tiempo
            de juego y los hábitos saludables. No prometas recompensas que no existan.
            """.formatted(puntos);
    }

    private ChatHistorialDTO toDTO(ChatHistorial e) {
        ChatHistorialDTO dto = new ChatHistorialDTO();
        dto.setIdChat(e.getIdChat());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setPregunta(e.getPregunta());
        dto.setRespuesta(e.getRespuesta());
        dto.setRolUsuario(e.getRolUsuario());
        dto.setCreadoEn(e.getCreadoEn());
        return dto;
    }
}

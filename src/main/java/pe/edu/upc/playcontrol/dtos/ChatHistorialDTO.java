package pe.edu.upc.playcontrol.dtos;

import java.time.OffsetDateTime;

// Item del historial listado en /api/chatbot/historial/{idUsuario}
public class ChatHistorialDTO {

    private Integer idChat;
    private Integer usuarioId;
    private String pregunta;
    private String respuesta;
    private String rolUsuario;
    private OffsetDateTime creadoEn;

    public ChatHistorialDTO() {}

    public Integer getIdChat() { return idChat; }
    public void setIdChat(Integer idChat) { this.idChat = idChat; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
    public OffsetDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(OffsetDateTime creadoEn) { this.creadoEn = creadoEn; }
}

package pe.edu.upc.playcontrol.dtos;

import java.time.OffsetDateTime;

// Respuesta que devuelve /api/chatbot/preguntar
public class ChatRespuestaDTO {

    private Integer idChat;
    private String pregunta;
    private String respuesta;
    private String rolUsuario;
    private OffsetDateTime creadoEn;

    public ChatRespuestaDTO() {}

    public Integer getIdChat() { return idChat; }
    public void setIdChat(Integer idChat) { this.idChat = idChat; }
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
    public OffsetDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(OffsetDateTime creadoEn) { this.creadoEn = creadoEn; }
}

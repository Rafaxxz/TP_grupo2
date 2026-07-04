package pe.edu.upc.playcontrol.dtos;

/**
 * DTO para mensajes de chat del chatbot
 * Request que envía el frontend: qué usuario pregunta y su mensaje
 */
public class ChatMensajeDTO {

    private Integer idUsuario;
    private String mensaje;

    public ChatMensajeDTO() {
    }

    public ChatMensajeDTO(Integer idUsuario, String mensaje) {
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

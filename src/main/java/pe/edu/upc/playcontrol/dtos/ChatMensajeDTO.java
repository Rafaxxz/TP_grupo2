package pe.edu.upc.playcontrol.dtos;

// Request que envía el frontend: qué usuario pregunta y su mensaje
public class ChatMensajeDTO {

    private Integer idUsuario;
    private String mensaje;

    public ChatMensajeDTO() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

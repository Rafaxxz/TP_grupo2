package pe.edu.upc.playcontrol.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AlertaDTO {

    private UUID idAlerta;
    private UUID usuarioId;
    private UUID sesionId;
    private String tipo;
    private String mensaje;
    private String nivel;
    private OffsetDateTime emitidaEn;
    private Boolean leida;

    public AlertaDTO() {
    }

    public UUID getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(UUID idAlerta) {
        this.idAlerta = idAlerta;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getSesionId() {
        return sesionId;
    }

    public void setSesionId(UUID sesionId) {
        this.sesionId = sesionId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public OffsetDateTime getEmitidaEn() {
        return emitidaEn;
    }

    public void setEmitidaEn(OffsetDateTime emitidaEn) {
        this.emitidaEn = emitidaEn;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }
}

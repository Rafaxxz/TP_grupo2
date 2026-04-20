package pe.edu.upc.playcontrol.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

public class LimiteTiempoDTO {

    private UUID idLimite;
    private UUID usuarioId;
    private String tipo;
    private Integer minutosMaximos;
    private Boolean bloqueoActivo;
    private Boolean notificar;
    private OffsetDateTime actualizadoEn;

    public LimiteTiempoDTO() {
    }

    public UUID getIdLimite() {
        return idLimite;
    }

    public void setIdLimite(UUID idLimite) {
        this.idLimite = idLimite;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getMinutosMaximos() {
        return minutosMaximos;
    }

    public void setMinutosMaximos(Integer minutosMaximos) {
        this.minutosMaximos = minutosMaximos;
    }

    public Boolean getBloqueoActivo() {
        return bloqueoActivo;
    }

    public void setBloqueoActivo(Boolean bloqueoActivo) {
        this.bloqueoActivo = bloqueoActivo;
    }

    public Boolean getNotificar() {
        return notificar;
    }

    public void setNotificar(Boolean notificar) {
        this.notificar = notificar;
    }

    public OffsetDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(OffsetDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
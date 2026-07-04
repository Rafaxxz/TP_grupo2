package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class LimiteTiempoDTO {

    private Integer idLimite;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "El tipo de límite es obligatorio")
    private String tipo;

    @NotNull(message = "Los minutos máximos son obligatorios")
    private Integer minutosMaximos;

    private Boolean bloqueoActivo;
    private Boolean notificar;
    private OffsetDateTime actualizadoEn;

    public LimiteTiempoDTO() {
    }

    public LimiteTiempoDTO(Integer usuarioId, String tipo, Integer minutosMaximos) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.minutosMaximos = minutosMaximos;
    }

    // ...existing getters and setters...
    public Integer getIdLimite() {
        return idLimite;
    }

    public void setIdLimite(Integer idLimite) {
        this.idLimite = idLimite;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
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

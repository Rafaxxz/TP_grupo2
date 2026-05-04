package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import java.time.OffsetDateTime;

public class LimiteTiempoDTO {
<<<<<<< HEAD
    private Integer idLimite;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "El tipo de límite es obligatorio")
=======

    private UUID idLimite;
    private UUID usuarioId;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
    private String tipo;

    @NotNull(message = "Los minutos máximos son obligatorios")
    private Integer minutosMaximos;

    private Boolean bloqueoActivo;
    private Boolean notificar;
    private OffsetDateTime actualizadoEn;
<<<<<<< HEAD
}
=======

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
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

package pe.edu.upc.playcontrol.dtos;

<<<<<<< HEAD
import jakarta.validation.constraints.NotNull;
import lombok.Data;

=======
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class SesionJuegoDTO {
<<<<<<< HEAD
    private Integer idSesion;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El juegoId es obligatorio")
    private Integer juegoId;

    @NotNull(message = "El inicio de la sesión es obligatorio")
=======

    private UUID idSesion;
    private UUID usuarioId;
    private UUID juegoId;
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2
    private OffsetDateTime inicio;

    private OffsetDateTime fin;
    private Integer duracionMinutos;
    private LocalDate fecha;
<<<<<<< HEAD
}
=======

    public SesionJuegoDTO() {
    }

    public UUID getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(UUID idSesion) {
        this.idSesion = idSesion;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(UUID juegoId) {
        this.juegoId = juegoId;
    }

    public OffsetDateTime getInicio() {
        return inicio;
    }

    public void setInicio(OffsetDateTime inicio) {
        this.inicio = inicio;
    }

    public OffsetDateTime getFin() {
        return fin;
    }

    public void setFin(OffsetDateTime fin) {
        this.fin = fin;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
>>>>>>> ac7a2e12c04e142efe7adb01912433c539770ad2

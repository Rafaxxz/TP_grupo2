package pe.edu.upc.playcontrol.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SesionJuegoDTO {

    private UUID idSesion;
    private UUID usuarioId;
    private UUID juegoId;
    private OffsetDateTime inicio;
    private OffsetDateTime fin;
    private Integer duracionMinutos;
    private LocalDate fecha;

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
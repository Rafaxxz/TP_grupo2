package pe.edu.upc.playcontrol.dtos;

import java.time.OffsetDateTime;

public class SesionJuegoRequest {
    private Integer usuarioId;
    private Integer juegoId;
    private OffsetDateTime inicio;
    private OffsetDateTime fin;

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getJuegoId() { return juegoId; }
    public void setJuegoId(Integer juegoId) { this.juegoId = juegoId; }
    public OffsetDateTime getInicio() { return inicio; }
    public void setInicio(OffsetDateTime inicio) { this.inicio = inicio; }
    public OffsetDateTime getFin() { return fin; }
    public void setFin(OffsetDateTime fin) { this.fin = fin; }
}

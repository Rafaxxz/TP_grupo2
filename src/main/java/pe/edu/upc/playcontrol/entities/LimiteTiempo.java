package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "limite_tiempo")
public class LimiteTiempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_limite")
    private Integer idLimite;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "minutos_maximos", nullable = false)
    private Integer minutosMaximos;

    @Column(name = "bloqueo_activo", nullable = false)
    private Boolean bloqueoActivo;

    @Column(name = "notificar", nullable = false)
    private Boolean notificar;

    @Column(name = "actualizado_en")
    private OffsetDateTime actualizadoEn;

    public LimiteTiempo() {
    }

    public UUID getIdLimite() {
        return idLimite;
    }

    public void setIdLimite(UUID idLimite) {
        this.idLimite = idLimite;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
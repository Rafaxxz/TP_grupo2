package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    private SesionJuego sesionJuego;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "emitida_en")
    private OffsetDateTime emitidaEn;

    @Column(name = "leida")
    private Boolean leida;

    public Alerta() {
    }

    public UUID getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(UUID idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public SesionJuego getSesionJuego() {
        return sesionJuego;
    }

    public void setSesionJuego(SesionJuego sesionJuego) {
        this.sesionJuego = sesionJuego;
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
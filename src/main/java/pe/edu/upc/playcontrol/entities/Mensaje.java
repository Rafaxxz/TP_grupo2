package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "mensaje")
@Getter @Setter @NoArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_mensaje", updatable = false, nullable = false)
    private UUID idMensaje;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "contexto", nullable = false, length = 20)
    private String contexto = "familiar";

    @Column(name = "enviado_en", nullable = false)
    private OffsetDateTime enviadoEn;

    @Column(name = "leido", nullable = false)
    private Boolean leido = false;

    @PrePersist
    public void prePersist() {
        if (enviadoEn == null) enviadoEn = OffsetDateTime.now();
        if (leido == null) leido = false;
        if (contexto == null) contexto = "familiar";
    }
}

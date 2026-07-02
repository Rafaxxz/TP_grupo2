package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "chat_historial")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ChatHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Integer idChat;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "pregunta", nullable = false, columnDefinition = "TEXT")
    private String pregunta;

    @Column(name = "respuesta", nullable = false, columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "rol_usuario", length = 20)
    private String rolUsuario;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn;

    @PrePersist
    public void prePersist() {
        if (creadoEn == null) creadoEn = OffsetDateTime.now();
    }
}

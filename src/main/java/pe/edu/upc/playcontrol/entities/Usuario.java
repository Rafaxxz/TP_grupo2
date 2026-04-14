package pe.edu.upc.playcontrol.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {


    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    private UUID idUsuario;

    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "password_hash", nullable = false, columnDefinition = "TEXT")
    private String passwordHash;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
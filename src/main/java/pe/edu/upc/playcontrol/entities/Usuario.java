package pe.edu.upc.playcontrol.entities;
<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
=======

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario", updatable = false, nullable = false)
    private UUID idUsuario;
>>>>>>> fabrizzio-salvador

    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

<<<<<<< HEAD
    @Column(name = "password_hash", nullable = false, columnDefinition = "TEXT")
    private String passwordHash;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "puntos_totales")
    private Integer puntosTotales;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "padre_id")
    private Integer padreId;

    @Column(name = "created_at", nullable = false)
=======
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // Referencia a sí mismo: un usuario puede tener un padre (otro usuario)
    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Usuario padre;

    @Column(name = "puntos_totales", nullable = false)
    private Integer puntosTotales = 0;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "created_at", nullable = false, updatable = false)
>>>>>>> fabrizzio-salvador
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (puntosTotales == null) puntosTotales = 0;
<<<<<<< HEAD
=======
        if (estado == null) estado = true;
>>>>>>> fabrizzio-salvador
    }
}

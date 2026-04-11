package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

// @Entity le dice a JPA que esta clase representa una tabla en la base de datos.
// Hibernate (la implementación JPA que usa Spring Boot) la gestionará automáticamente:
// creará el mapeo entre los campos de Java y las columnas de PostgreSQL.
@Entity

// @Table especifica el nombre exacto de la tabla en la BD.
// Sin esto, JPA intentaría usar el nombre de la clase ("logro" en lowercase),
// pero es buena práctica ser explícito para evitar ambigüedades.
@Table(name = "logro")

// Lombok genera automáticamente getters, setters y constructores en tiempo de compilación.
// @Getter + @Setter evitan escribir ~60 líneas de código boilerplate.
// @NoArgsConstructor es obligatorio para JPA (necesita instanciar la clase sin argumentos).
// @AllArgsConstructor es útil para crear objetos completos en una sola línea.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Logro {

    // @Id marca este campo como la clave primaria de la tabla.
    // @GeneratedValue(strategy = UUID) le delega a Hibernate la generación del UUID,
    // equivalente al DEFAULT gen_random_uuid() del DDL de PostgreSQL.
    // updatable = false: el ID nunca cambia después de crearse.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_logro", updatable = false, nullable = false)
    private UUID idLogro;

    // nullable = false refleja el NOT NULL del DDL.
    // length = 120 refleja el VARCHAR(120) — JPA lo usa al validar y generar esquemas.
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    // columnDefinition = "TEXT" mapea exactamente al tipo TEXT de PostgreSQL.
    // Sin nullable = false porque este campo sí puede ser null en el DDL.
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "icono_url", columnDefinition = "TEXT")
    private String iconoUrl;

    // int (primitivo) ya tiene valor por defecto 0 en Java,
    // lo que coincide con el DEFAULT 0 del DDL.
    @Column(name = "puntos_otorgados", nullable = false)
    private int puntosOtorgados;

    // El campo criterio en PostgreSQL tiene un CHECK constraint con 4 valores posibles
    // (racha, dias_limite, meta_semanal, reto_completado).
    // Usamos String en vez de un @Enumerated porque los valores usan snake_case lowercase,
    // que no es convencional en Java enums. String es más simple y suficiente para el curso.
    @Column(name = "criterio", nullable = false, length = 30)
    private String criterio;

    @Column(name = "valor_criterio", nullable = false)
    private int valorCriterio;
}

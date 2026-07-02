package pe.edu.upc.playcontrol.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "contenido_educativo")
@Getter @Setter @NoArgsConstructor
public class ContenidoEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenido")
    private Integer idContenido;

    @Column(name = "titulo", nullable = false, length = 250)
    private String titulo;

    @Column(name = "autor", length = 150)
    private String autor;

    @Column(name = "fuente", length = 250)
    private String fuente;

    @Column(name = "tipo", nullable = false, length = 25)
    private String tipo;

    @Column(name = "url")
    private String url;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "publicado_en")
    private LocalDate publicadoEn;
}

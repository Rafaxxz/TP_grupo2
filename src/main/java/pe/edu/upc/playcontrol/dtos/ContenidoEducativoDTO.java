package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ContenidoEducativoDTO {
    private UUID idContenido;
    private String titulo;
    private String autor;
    private String fuente;
    private String tipo;
    private String url;
    private String contenido;
    private LocalDate publicadoEn;
}

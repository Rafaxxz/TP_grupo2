package pe.edu.upc.playcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ContenidoEducativoDTO {
    private Integer idContenido;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String autor;
    private String fuente;

    @NotBlank(message = "El tipo de contenido es obligatorio")
    private String tipo;

    @NotBlank(message = "La URL es obligatoria")
    private String url;

    private String contenido;
    private LocalDate publicadoEn;
}

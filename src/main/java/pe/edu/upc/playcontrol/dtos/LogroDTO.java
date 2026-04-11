package pe.edu.upc.playcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

// El DTO (Data Transfer Object) es el objeto que viaja entre el cliente y la API.
// Su propósito es desacoplar la entidad JPA del contrato HTTP:
//   - Evita exponer campos internos o relaciones JPA complejas al cliente.
//   - Permite moldear los datos según lo que el cliente realmente necesita.
//   - Protege contra problemas de serialización circular en relaciones @ManyToOne/@OneToMany.
// No tiene anotaciones JPA — es un POJO simple que Jackson serializa a/desde JSON.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogroDTO {

    private UUID idLogro;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
    private int puntosOtorgados;
    private String criterio;       // valores válidos: racha, dias_limite, meta_semanal, reto_completado
    private int valorCriterio;
}

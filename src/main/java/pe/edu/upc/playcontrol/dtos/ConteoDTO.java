package pe.edu.upc.playcontrol.dtos;

public class ConteoDTO {
    private String etiqueta;
    private Long valor;

    public ConteoDTO() {}

    public ConteoDTO(String etiqueta, Long valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    public Long getValor() { return valor; }
    public void setValor(Long valor) { this.valor = valor; }
}

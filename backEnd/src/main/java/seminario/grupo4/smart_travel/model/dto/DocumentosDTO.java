package seminario.grupo4.smart_travel.model.dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class DocumentosDTO {
    private long id;
    private String nombreDocumento;
    private String tipo;
    private long idViaje;

    public DocumentosDTO() {
    }

    public DocumentosDTO(String nombreDocumento, String tipo, long idViaje) {
        this.nombreDocumento = nombreDocumento;
        this.tipo = tipo;
        this.idViaje = idViaje;
    }

    @Override
    public String toString() {
        return "DocumentosDTO{" +
                "nombreDocumento='" + nombreDocumento + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idViaje=" + idViaje +
                '}';
    }
}

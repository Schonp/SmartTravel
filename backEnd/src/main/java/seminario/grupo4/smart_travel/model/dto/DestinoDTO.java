package seminario.grupo4.smart_travel.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DestinoDTO {
    private String ciudadDestino;
    private Date fechaInicio;
    private Date fechaFin;
    private Long viajeId;

    public DestinoDTO() {
    }

    public DestinoDTO(String ciudadDestino, Date fechaInicio, Date fechaFin, Long viajeId) {
        this.ciudadDestino = ciudadDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.viajeId = viajeId;
    }

    @Override
    public String toString() {
        return "DestinoDTO{" +
                "ciudadDestino='" + ciudadDestino + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", viajeId=" + viajeId +
                '}';
    }
}

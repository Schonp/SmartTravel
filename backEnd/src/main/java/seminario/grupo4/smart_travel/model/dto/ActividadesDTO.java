package seminario.grupo4.smart_travel.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ActividadesDTO {
    private String nombreActividad;
    private String lugar;
    private Date fecha;
    private Long viajeId;

    public ActividadesDTO() {
    }

    public ActividadesDTO(String nombreActividad, String lugar, Date fecha, Long viajeId) {
        this.nombreActividad = nombreActividad;
        this.lugar = lugar;
        this.fecha = fecha;
        this.viajeId = viajeId;
    }

    @Override
    public String toString() {
        return "ActividadesDTO{" +
                "nombreActividad='" + nombreActividad + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", viajeId=" + viajeId +
                '}';
    }
}

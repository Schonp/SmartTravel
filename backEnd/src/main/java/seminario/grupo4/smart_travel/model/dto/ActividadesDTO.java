package seminario.grupo4.smart_travel.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActividadesDTO {
    private String nombreActividad;
    private String lugar;
    private String fecha;
    private Long viajeId;

    public ActividadesDTO() {
    }

    public ActividadesDTO(String nombreActividad, String lugar, String fecha, Long viajeId) {
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

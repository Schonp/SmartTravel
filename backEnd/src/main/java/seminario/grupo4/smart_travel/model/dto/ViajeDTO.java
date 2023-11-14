package seminario.grupo4.smart_travel.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Usuario;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ViajeDTO {
    private String nombreViaje;
    private long idUsuario;
    private Long ViajeId;

    public ViajeDTO() {
    }

    public ViajeDTO(String nombreViaje, Date fechaInicio, Date fechaFin, long idUsuario) {
        this.nombreViaje = nombreViaje;
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "ViajeDTO{" +
                "nombreViaje='" + nombreViaje + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}

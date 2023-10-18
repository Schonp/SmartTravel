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
    private Date fechaInicio;
    private Date fechaFin;
    private long idUsuario;

    public ViajeDTO() {
    }

    public ViajeDTO(String nombreViaje, Date fechaInicio, Date fechaFin, long idUsuario) {
        this.nombreViaje = nombreViaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "ViajeDTO{" +
                "nombreViaje='" + nombreViaje + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", idUsuario=" + idUsuario +
                '}';
    }
}

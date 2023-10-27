package seminario.grupo4.smart_travel.model.dto;

import jakarta.persistence.*;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Viaje;

public class GastoDTO {
    private String nombreGasto;
    private double monto;
    private long idComprador;
    private long idViaje;

    public GastoDTO() {
    }

    public GastoDTO(String nombreGasto, double monto, long idComprador, long idViaje) {
        this.nombreGasto = nombreGasto;
        this.monto = monto;
        this.idComprador = idComprador;
        this.idViaje = idViaje;
    }

    public String getNombreGasto() {
        return nombreGasto;
    }

    public double getMonto() {
        return monto;
    }

    public long getIdComprador() {
        return idComprador;
    }

    public long getIdViaje() {
        return idViaje;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setIdComprador(long idComprador) {
        this.idComprador = idComprador;
    }

    public void setIdViaje(long idViaje) {
        this.idViaje = idViaje;
    }

    @Override
    public String toString() {
        return "GastoDTO{" +
                "nombreGasto='" + nombreGasto + '\'' +
                ", monto=" + monto +
                ", idComprador=" + idComprador +
                ", idViaje=" + idViaje +
                '}';
    }
}

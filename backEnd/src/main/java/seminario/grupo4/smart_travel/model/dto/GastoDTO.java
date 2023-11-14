package seminario.grupo4.smart_travel.model.dto;

import jakarta.persistence.*;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Viaje;

public class GastoDTO {
    private String nombreGasto;
    private double monto;
    private String tipo;
    private long idComprador;
    private long idViaje;
    private Long GastoID;

    public GastoDTO() {
    }

    public GastoDTO(String nombreGasto, double monto, String tipo, long idComprador, long idViaje) {
        this.nombreGasto = nombreGasto;
        this.monto = monto;
        this.tipo = tipo;
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

    public Long getGastoID() {
        return GastoID;
    }

    public void setGastoID(Long gastoID) {
        GastoID = gastoID;
    }

    public String getTipo() {
        return tipo;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

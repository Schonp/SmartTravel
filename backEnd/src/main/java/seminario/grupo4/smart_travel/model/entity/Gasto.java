package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gastos")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreGasto;
    private double monto;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Miembro comprador;
    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;


    public Gasto() {
    }

    public Gasto(String nombreGasto, double monto, String tipo) {
        this.nombreGasto = nombreGasto;
        this.monto = monto;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public String getNombreGasto() {
        return nombreGasto;
    }

    public double getMonto() {
        return monto;
    }

    public Miembro getComprador() {
        return comprador;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setComprador(Miembro comprador) {
        this.comprador = comprador;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", nombreGasto='" + nombreGasto + '\'' +
                ", monto=" + monto +
                ", comprador=" + comprador +
                ", viaje=" + viaje +
                '}';
    }
}

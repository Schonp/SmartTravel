package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gastos")
@Getter
@Setter
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;


    public Gasto() {
    }

    public Gasto(String nombreGasto, double monto, String tipo) {
        this.nombreGasto = nombreGasto;
        this.monto = monto;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", nombreGasto='" + nombreGasto + '\'' +
                ", monto=" + monto +
                ", comprador=" + comprador +
                '}';
    }
}

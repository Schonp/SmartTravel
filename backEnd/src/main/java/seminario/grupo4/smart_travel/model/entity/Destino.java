package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "destinos")
@Getter
@Setter
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ciudadDestino;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;


    public Destino() {
    }

    public Destino(String ciudadDestino, Date fechaInicio, Date fechaFin) {
        this.ciudadDestino = ciudadDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Destino{" +
                "id=" + id +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", viaje=" + viaje +
                '}';
    }
}

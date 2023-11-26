package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "actividades")
@Getter
@Setter
public class Actividades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreActividad;
    private String lugar;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;

    public Actividades() {
    }

    public Actividades(String nombreActividad, String lugar, Date fecha) {
        this.nombreActividad = nombreActividad;
        this.lugar = lugar;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Actividades{" +
                "id=" + id +
                ", nombreActividad='" + nombreActividad + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}

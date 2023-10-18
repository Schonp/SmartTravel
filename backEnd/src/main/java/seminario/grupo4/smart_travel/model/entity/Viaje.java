package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "viajes")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreViaje;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @OneToMany(mappedBy = "viaje")
    private List<Miembro> miembros;
    @OneToMany(mappedBy = "viaje")
    private List<Gasto> gastos;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Viaje() {
    }

    public Viaje(Long id, String nombreViaje, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombreViaje = nombreViaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public String getNombreViaje() {
        return nombreViaje;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreViaje(String nombreViaje) {
        this.nombreViaje = nombreViaje;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", nombreViaje='" + nombreViaje + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}

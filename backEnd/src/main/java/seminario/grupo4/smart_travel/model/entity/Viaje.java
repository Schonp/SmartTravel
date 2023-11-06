package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "viajes")
@Getter
@Setter
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreViaje;
    @OneToMany(mappedBy = "viaje")
    private List<Miembro> miembros = new ArrayList<>();
    @OneToMany(mappedBy = "viaje")
    private List<Gasto> gastos= new ArrayList<>();
    @OneToMany(mappedBy = "viaje")
    private List<Documento> documentos= new ArrayList<>();
    @OneToMany(mappedBy = "viaje")
    private List<Destino> destinos= new ArrayList<>();
    @OneToMany(mappedBy = "viaje")
    private List<Actividades> actividades = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Viaje() {
    }

    public Viaje(Long id, String nombreViaje) {
        this.id = id;
        this.nombreViaje = nombreViaje;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", nombreViaje='" + nombreViaje + '\'' +
                '}';
    }
}

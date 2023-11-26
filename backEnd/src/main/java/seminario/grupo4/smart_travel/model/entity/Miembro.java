package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "miembros")
@Getter
@Setter
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String email;
    private double balance;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;
    @OneToMany(mappedBy = "comprador")
    private List<Gasto> gastos = new ArrayList<>();

    public Miembro() {
    }

    public Miembro(String nombre, String email, double balance) {
        this.nombre = nombre;
        this.email = email;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Miembro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}

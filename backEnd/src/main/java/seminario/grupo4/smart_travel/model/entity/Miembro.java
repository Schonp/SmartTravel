package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "miembros")
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String email;
    private double balance;
    @ManyToOne
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

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
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

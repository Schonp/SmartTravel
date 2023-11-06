package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreUs;
    private String contraseña;
    private String email;
    @OneToMany(mappedBy = "usuario")
    private List<Viaje> viajes = new ArrayList<>();


    public Usuario() {
    }

    public Usuario(String nombreUs, String contraseña, String email) {
        this.nombreUs = nombreUs;
        this.contraseña = contraseña;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUs='" + nombreUs + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

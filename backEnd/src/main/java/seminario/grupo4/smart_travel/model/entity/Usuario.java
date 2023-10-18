package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
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

    public Usuario(long id, String nombreUs, String contraseña, String email) {
        this.id = id;
        this.nombreUs = nombreUs;
        this.contraseña = contraseña;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNombreUs() {
        return nombreUs;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getEmail() {
        return email;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombreUs(String nombreUs) {
        this.nombreUs = nombreUs;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }
}

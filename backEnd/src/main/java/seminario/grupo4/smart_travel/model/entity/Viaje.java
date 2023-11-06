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
    @OneToMany(mappedBy = "viaje")
    private List<Miembro> miembros;
    @OneToMany(mappedBy = "viaje")
    private List<Gasto> gastos;
    @OneToMany(mappedBy = "viaje")
    private List<Documento> documentos;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;




    public Viaje() {
    }

    public Viaje(Long id, String nombreViaje) {
        this.id = id;
        this.nombreViaje = nombreViaje;
    }

    public Long getId() {
        return id;
    }

    public String getNombreViaje() {
        return nombreViaje;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreViaje(String nombreViaje) {
        this.nombreViaje = nombreViaje;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", nombreViaje='" + nombreViaje + '\'' +
                '}';
    }
}

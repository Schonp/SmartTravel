package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


@Entity
@Table(name = "documentos")
@Getter
@Setter
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreDocumento;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] datosDocumento;
    private String tipo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;
    public Documento() {

    }

    public Documento(String nombreDocumento, byte[] datosDocumento, String tipo, Viaje viaje) {
        this.nombreDocumento = nombreDocumento;
        this.tipo = tipo;
        this.datosDocumento = datosDocumento;
        this.viaje = viaje;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", datosDocumento=" + Arrays.toString(datosDocumento) +
                ", viaje=" + viaje +
                '}';
    }
}

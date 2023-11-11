package seminario.grupo4.smart_travel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "encuestas")
@Getter
@Setter
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fomsId;
    private String url;
    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;


    public Encuesta() {
    }

    public Encuesta(String fomsId, String url) {
        this.fomsId = fomsId;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "id=" + id +
                ", fomsId='" + fomsId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

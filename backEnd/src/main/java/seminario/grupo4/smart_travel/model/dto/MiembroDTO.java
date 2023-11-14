package seminario.grupo4.smart_travel.model.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import seminario.grupo4.smart_travel.model.entity.Viaje;
@Getter
@Setter
public class MiembroDTO {
    private String nombre;
    private String email;
    private double balance;
    private Long idViaje;
    private Long MiembroId;

    public MiembroDTO() {
    }
    public MiembroDTO(String nombre, String email, double balance, long idViaje) {
        this.nombre = nombre;
        this.email = email;
        this.balance = balance;
        this.idViaje = idViaje;
    }

    @Override
    public String toString() {
        return "MiembroDTO{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", idViaje=" + idViaje +
                '}';
    }
}

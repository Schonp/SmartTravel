package seminario.grupo4.smart_travel.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String nombreUs;
//    private String contraseña;
    private String email;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombreUs, String contraseña, String email) {
        this.nombreUs = nombreUs;
//        this.contraseña = contraseña;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nombreUs='" + nombreUs + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

package seminario.grupo4.smart_travel.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncuestaDTO {
    private String fomsId;
    private String url;
    private long viajeId;

    public EncuestaDTO() {
    }

    public EncuestaDTO(String fomsId, String url, long viajeId) {
        this.fomsId = fomsId;
        this.url = url;
        this.viajeId = viajeId;
    }

    @Override
    public String toString() {
        return "EncuestaDTO{" +
                "fomsId='" + fomsId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

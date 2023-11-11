package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Encuesta;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IEncuestasService {
    List<Encuesta> getAll();
    Encuesta getById(long id);
    List<Encuesta> getByViaje(Viaje viaje);
    void saveEncuesta(Encuesta encuesta);
    void deleteEncusta(Encuesta encuesta);

}

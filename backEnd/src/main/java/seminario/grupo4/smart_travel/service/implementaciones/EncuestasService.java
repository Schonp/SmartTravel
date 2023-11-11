package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Encuesta;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IEncuestasDAO;
import seminario.grupo4.smart_travel.service.interfaces.IEncuestasService;

import java.util.List;

@Service
public class EncuestasService implements IEncuestasService {
     @Autowired
     private IEncuestasDAO encuestasDAO;

    @Override
    public List<Encuesta> getAll() {
        return encuestasDAO.getAll();
    }

    @Override
    public Encuesta getById(long id) {
        return encuestasDAO.getById(id);
    }

    @Override
    public List<Encuesta> getByViaje(Viaje viaje) {
        return encuestasDAO.getByViaje(viaje);
    }

    @Override
    public void saveEncuesta(Encuesta encuesta) {
        encuestasDAO.saveEncuesta(encuesta);
    }

    @Override
    public void deleteEncusta(Encuesta encuesta) {
        encuestasDAO.deleteEncusta(encuesta);
    }
}

package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Actividades;
import seminario.grupo4.smart_travel.repository.interfaces.IActividadDAO;
import seminario.grupo4.smart_travel.service.interfaces.IActividadesService;

import java.util.List;
@Service
public class ActividadesService implements IActividadesService {

    @Autowired
    private IActividadDAO actividadDAO;

    @Override
    public List<Actividades> findAll() {
        return actividadDAO.findAll();
    }

    @Override
    public Actividades findById(long id) {
        return actividadDAO.findById(id);
    }

    @Override
    public void save(Actividades actividades) {
        actividadDAO.save(actividades);
    }

    @Override
    public void update(long id, Actividades actividades) {
        Actividades actividadesActual = actividadDAO.findById(id);

        if (actividadesActual != null){
            actividadesActual.setNombreActividad(actividades.getNombreActividad());
            actividadesActual.setFecha(actividades.getFecha());
            actividadesActual.setLugar(actividades.getLugar());
        }
    }

    @Override
    public void deleteById(long id) {
        actividadDAO.deleteById(id);
    }
}

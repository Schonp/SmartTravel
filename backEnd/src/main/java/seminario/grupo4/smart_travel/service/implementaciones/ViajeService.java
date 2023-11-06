package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IViajeDAO;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.List;

@Service
public class ViajeService implements IViajeService {
    @Autowired
    private IViajeDAO viajeDAO;

    @Override
    public List<Viaje> findAll() {
        return viajeDAO.findAll();
    }

    @Override
    public Viaje findById(long id) {
        return viajeDAO.findById(id);
    }

    @Override
    public void save(Viaje viaje) {
        viajeDAO.save(viaje);
    }

    @Override
    public void update(long id, Viaje viaje) {
        Viaje viajeActual = viajeDAO.findById(id);

        if (viajeActual != null){
            viajeActual.setNombreViaje(viaje.getNombreViaje());
            viajeActual.setMiembros(viaje.getMiembros());
            viajeActual.setGastos(viaje.getGastos());
            viajeActual.setUsuario(viaje.getUsuario());

            viajeDAO.save(viajeActual);
        }
    }

    @Override
    public void deleteById(long id) {
        viajeDAO.deleteById(id);
    }
}

package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Destino;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IDestinoDAO;
import seminario.grupo4.smart_travel.service.interfaces.IDestinoService;

import java.util.List;

@Service
public class DestinoService implements IDestinoService {
    @Autowired
    private IDestinoDAO destinoDAO;

    @Override
    public List<Destino> findAll() {
        return destinoDAO.findAll();
    }

    @Override
    public Destino findById(long id) {
        return destinoDAO.findById(id);
    }

    @Override
    public void save(Destino destino) {
        destinoDAO.save(destino);
    }

    @Override
    public void update(long id, Destino destino) {
        Destino destinoActual = destinoDAO.findById(id);

        if (destinoActual != null){
           destinoActual.setCiudadDestino(destino.getCiudadDestino());
           destinoActual.setFechaInicio(destino.getFechaInicio());
           destinoActual.setFechaFin(destino.getFechaFin());

           destinoDAO.save(destinoActual);
        }
    }

    @Override
    public void deleteById(long id) {
        destinoDAO.deleteById(id);
    }

    @Override
    public List<Destino> findByViaje(Viaje viaje) {
        return destinoDAO.findByViaje(viaje);
    }
}

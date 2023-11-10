package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IMiembroDAO;
import seminario.grupo4.smart_travel.service.interfaces.IMiembroService;

import java.util.List;

@Service
public class MiembroService implements IMiembroService {
    @Autowired
    private IMiembroDAO miembroDAO;

    @Override
    public List<Miembro> findAll() {
        List<Miembro> miembros = miembroDAO.findAll();
        return miembros;
    }

    @Override
    public List<Miembro> findbyIdVieje(long id) {
        List<Miembro> miembros = miembroDAO.findbyIdVieje(id);
        return miembros;
    }

    @Override
    public Miembro findById(long id) {
        return miembroDAO.findById(id);
    }

    @Override
    public void save(Miembro miembro) {
        miembroDAO.save(miembro);
    }

    @Override
    public void update(long id, Miembro miembro) {
        Miembro miembroViejo = miembroDAO.findById(id);

        if(miembroViejo != null){
            miembroViejo.setNombre(miembro.getNombre());
            miembroViejo.setViaje(miembro.getViaje());
            miembroViejo.setEmail(miembro.getEmail());
            miembroViejo.setBalance(miembro.getBalance());
            miembroViejo.setGastos(miembro.getGastos());

            miembroDAO.save(miembroViejo);
        }
    }

    @Override
    public void deleteById(long id) {
        miembroDAO.deleteById(id);
    }

    @Override
    public List<Miembro> findByViaje(Viaje viaje) {
        return miembroDAO.findByViaje(viaje);
    }

    @Override
    public Miembro findByMiembro(Miembro miembro) {
        return miembroDAO.findByMiembro(miembro);
    }
}

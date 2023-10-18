package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.repository.interfaces.IGastoDAO;
import seminario.grupo4.smart_travel.repository.interfaces.IUsuarioDAO;
import seminario.grupo4.smart_travel.service.interfaces.IGastoService;

import java.util.List;
@Service
public class GastoService implements IGastoService {

    @Autowired
    private IGastoDAO gastoDAO;
    @Override
    public List<Gasto> findAll() {
        List<Gasto> gastos = gastoDAO.findAll();
        return gastos;
    }

    @Override
    public Gasto findById(long id) {
        return gastoDAO.findById(id);
    }

    @Override
    public void save(Gasto gasto) {
        gastoDAO.save(gasto);
    }

    @Override
    public void update(long id, Gasto gasto) {
        Gasto gastoActual = gastoDAO.findById(id);

        if(gastoActual!= null){
            gastoActual.setNombreGasto(gasto.getNombreGasto());
            gastoActual.setComprador(gasto.getComprador());
            gastoActual.setMonto(gasto.getMonto());
            gastoActual.setViaje(gasto.getViaje());

            gastoDAO.save(gastoActual);
        }
    }

    @Override
    public void deleteById(long id) {
        gastoDAO.deleteById(id);
    }
}

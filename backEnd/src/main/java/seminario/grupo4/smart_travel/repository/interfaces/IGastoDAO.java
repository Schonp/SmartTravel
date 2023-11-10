package seminario.grupo4.smart_travel.repository.interfaces;

import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IGastoDAO {
    List<Gasto> findAll();
    Gasto findById(long id);
    void save(Gasto miembro);
    void deleteById(long id);
    List<Gasto> findByViaje(Viaje viaje);
}

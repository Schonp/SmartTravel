package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IViajeService {
    List<Viaje> findAll();
    Viaje findById(long id);
    void save(Viaje viaje);
    void update(long id,Viaje viaje);
    void deleteById(long id);
}

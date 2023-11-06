package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Actividades;

import java.util.List;

public interface IActividadesService {
    List<Actividades> findAll();
    Actividades findById(long id);
    void save(Actividades actividades);
    void update(long id,Actividades actividades);
    void deleteById(long id);
}

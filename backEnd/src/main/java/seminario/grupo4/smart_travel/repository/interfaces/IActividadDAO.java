package seminario.grupo4.smart_travel.repository.interfaces;

import seminario.grupo4.smart_travel.model.entity.Actividades;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IActividadDAO {
    List<Actividades> findAll();
    Actividades findById(long id);
    void save(Actividades actividades);
    void deleteById(long id);
    List<Actividades> findByViaje(Viaje viaje);
}

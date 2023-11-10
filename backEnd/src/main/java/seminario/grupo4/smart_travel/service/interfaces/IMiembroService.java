package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IMiembroService {
    List<Miembro> findAll();
    List<Miembro> findbyIdVieje(long id);
    Miembro findById(long id);
    void save(Miembro miembro);
    void update(long id,Miembro miembro);
    void deleteById(long id);
    List<Miembro> findByViaje(Viaje viaje);
    Miembro findByMiembro(Miembro miembro);
}

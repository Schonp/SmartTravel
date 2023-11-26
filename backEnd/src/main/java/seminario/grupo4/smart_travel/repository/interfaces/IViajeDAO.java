package seminario.grupo4.smart_travel.repository.interfaces;

import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IViajeDAO {
    List<Viaje> findAll();
    Viaje findById(long id);
    void save(Viaje viaje);
    void deleteById(Viaje id);
    List<Viaje> findByUsuario(Usuario usuario);
}

package seminario.grupo4.smart_travel.repository.interfaces;

import seminario.grupo4.smart_travel.model.entity.Destino;

import java.util.List;

public interface IDestinoDAO {
    List<Destino> findAll();
    Destino findById(long id);
    void save(Destino destino);
    void deleteById(long id);
}

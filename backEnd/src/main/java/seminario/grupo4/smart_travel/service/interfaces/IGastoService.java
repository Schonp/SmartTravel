package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Gasto;

import java.util.List;

public interface IGastoService {
    List<Gasto> findAll();
    Gasto findById(long id);
    void save(Gasto gasto);
    void update(long id,Gasto gasto);
    void deleteById(long id);
}

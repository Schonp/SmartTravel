package seminario.grupo4.smart_travel.repository.interfaces;

import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Miembro;

import java.util.List;

public interface IMiembroDAO {
    List<Miembro> findAll();
    Miembro findById(long id);
    void save(Miembro miembro);
    void deleteById(long id);
    List<Miembro> findbyIdVieje(long id);
}

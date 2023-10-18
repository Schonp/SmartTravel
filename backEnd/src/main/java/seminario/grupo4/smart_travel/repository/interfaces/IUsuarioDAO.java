package seminario.grupo4.smart_travel.repository.interfaces;

import seminario.grupo4.smart_travel.model.entity.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    List<Usuario> findAll();
    Usuario findById(long id);
    void save(Usuario usuario);
    void deleteById(long id);
    Usuario findUser(String nombreUs, String contraseña);
}

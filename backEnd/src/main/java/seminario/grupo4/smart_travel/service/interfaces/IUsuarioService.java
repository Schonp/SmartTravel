package seminario.grupo4.smart_travel.service.interfaces;

import seminario.grupo4.smart_travel.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario findById(long id);
    void save(Usuario usuario);
    void update(long id,Usuario usuario);
    void deleteById(long id);
    Usuario findUser(String nombreUs, String contraseña);
}

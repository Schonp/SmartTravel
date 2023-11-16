package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.repository.interfaces.IUsuarioDAO;
import seminario.grupo4.smart_travel.service.interfaces.IUsuarioService;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioDAO.findAll();
        return usuarios;
    }

    @Override
    public Usuario findById(long id) {
        return usuarioDAO.findById(id);
    }

    @Override
    public void save(Usuario usuario) {
        usuario.setContraseña(new BCryptPasswordEncoder().encode(usuario.getContraseña()));

        usuarioDAO.save(usuario);
    }

    @Override
    public void update(long id, Usuario usuario) {
        Usuario usuarioActual = usuarioDAO.findById(id);

        if (usuarioActual != null){
            usuarioActual.setNombreUs(usuario.getNombreUs());
            //usuarioActual.setContraseña(usuario.getContraseña());
            usuarioActual.setEmail(usuario.getEmail());


            usuarioDAO.save(usuarioActual);
        }
    }

    @Override
    public void deleteById(long id) {
        usuarioDAO.deleteById(id);
    }

    @Override
    public Usuario findUser(String nombreUs, String contraseña) {
        Usuario usuario = usuarioDAO.findUser(nombreUs,contraseña);
        return usuario;
    }

}

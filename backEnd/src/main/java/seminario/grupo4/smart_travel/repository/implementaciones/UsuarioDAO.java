package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.repository.interfaces.IUsuarioDAO;


import java.util.List;

@Repository
public class UsuarioDAO implements IUsuarioDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Usuario> q = session.createQuery("FROM Usuario", Usuario.class);
        List<Usuario> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Usuario usuario = currentSession.get(Usuario.class, id);
        return usuario;
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.persist(usuario);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from Usuario where id=:idUsuario");
        query.setParameter("idUsuario",id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public Usuario findUser(String nombreUs, String contraseña) {
        Session session = entityManager.unwrap(Session.class);

        Query<Usuario> q = session.createQuery("FROM Usuario WHERE nombreUs=:nombreUs", Usuario.class);
        q.setParameter("nombreUs", nombreUs);

        Usuario retorno = (Usuario) q.getSingleResult();

        if(retorno != null && checkPassword(contraseña, retorno.getContraseña())) {
            return retorno;
        }
        else {
            return null;
        }
    }

    private boolean checkPassword(String contraseña, String contraseñaBD) {
        boolean retorno;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        retorno = passwordEncoder.matches(contraseña, contraseñaBD);

        return retorno;
    }
}

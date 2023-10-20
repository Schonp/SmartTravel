package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IViajeDAO;

import java.util.List;

@Repository
public class ViajeDAO implements IViajeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Viaje> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Viaje> q = session.createQuery("FROM Viaje", Viaje.class);
        List<Viaje> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Viaje findById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Viaje viaje = session.get(Viaje.class, id);
        return viaje;
    }

    @Override
    @Transactional
    public void save(Viaje viaje) {
        Session session = entityManager.unwrap(Session.class);

        session.persist(viaje);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query query = currentSession.createQuery("delete from Viaje where id=:id");

        query.setParameter("id",id);
        query.executeUpdate();
    }
}

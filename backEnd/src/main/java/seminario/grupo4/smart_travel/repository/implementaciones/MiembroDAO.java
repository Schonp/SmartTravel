package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IMiembroDAO;

import java.util.List;

@Repository
public class MiembroDAO implements IMiembroDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<Miembro> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Miembro> q = session.createQuery("FROM Miembro", Miembro.class);
        List<Miembro> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Miembro findById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Miembro miembro = currentSession.get(Miembro.class, id);
        return miembro;
    }

    @Override
    @Transactional
    public void save(Miembro miembro) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.persist(miembro);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("DELETE FROM Miembro WHERE id=:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Miembro> findbyIdVieje(long id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query query = currentSession.createQuery("FROM Miembro WHERE viaje.id=:id");
        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public List<Miembro> findByViaje(Viaje viaje) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Miembro> q = currentSession.createQuery("FROM Miembro WHERE viaje = :viaje", Miembro.class);
        q.setParameter("viaje", viaje);
        List<Miembro> miembros = q.getResultList();

        return miembros;
    }
}

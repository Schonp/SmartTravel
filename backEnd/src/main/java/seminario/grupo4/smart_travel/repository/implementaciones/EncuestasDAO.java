package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Encuesta;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IEncuestasDAO;

import java.util.List;

@Repository
public class EncuestasDAO implements IEncuestasDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Encuesta> getAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Encuesta> q = session.createQuery("FROM Encuesta", Encuesta.class);

        List<Encuesta> encuestas = q.getResultList();

        return encuestas;
    }

    @Override
    @Transactional(readOnly = true)
    public Encuesta getById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Encuesta encuesta = session.get(Encuesta.class, id);
        return encuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Encuesta> getByViaje(Viaje viaje) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Query<Encuesta> q = session.createQuery("FROM Encuesta WHERE viaje=:viaje", Encuesta.class);
            q.setParameter("viaje", viaje);

            List<Encuesta> encuestas = q.getResultList();

            return encuestas;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveEncuesta(Encuesta encuesta) {
        Session session = entityManager.unwrap(Session.class);

        session.persist(encuesta);
    }

    @Override
    @Transactional
    public void deleteEncusta(Encuesta encuesta) {
        Session session = entityManager.unwrap(Session.class);

        session.delete(encuesta);
    }
}

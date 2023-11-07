package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Actividades;
import seminario.grupo4.smart_travel.repository.interfaces.IActividadDAO;

import java.util.List;

@Repository
public class ActividadDAO implements IActividadDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional(readOnly = true)
    public List<Actividades> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Actividades> q = session.createQuery("FROM Actividades", Actividades.class);
        List<Actividades> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Actividades findById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Actividades actividades = session.get(Actividades.class, id);

        return actividades;
    }

    @Override
    @Transactional
    public void save(Actividades actividades) {
        Session session = entityManager.unwrap(Session.class);

        session.persist(actividades);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Query<Actividades> query = session.createQuery("delete from Actividades where id=:idActividades");
        query.setParameter("idActividades",id);
        query.executeUpdate();
    }
}

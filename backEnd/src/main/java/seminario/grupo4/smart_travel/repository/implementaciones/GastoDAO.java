package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IGastoDAO;

import java.util.List;

@Repository
public class GastoDAO implements IGastoDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Gasto> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Gasto> q = session.createQuery("FROM Gasto", Gasto.class);
        List<Gasto> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Gasto findById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Gasto gasto = currentSession.get(Gasto.class, id);
        return gasto;
    }

    @Override
    @Transactional
    public void save(Gasto gasto) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.persist(gasto);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Gasto> query = currentSession.createQuery("delete from Gasto where id=:idGasto");
        query.setParameter("idGasto",id);
        query.executeUpdate();
    }

    @Override
    public List<Gasto> findByViaje(Viaje viaje) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Gasto> q = currentSession.createQuery("FROM Gasto WHERE viaje = :viaje", Gasto.class);
        q.setParameter("viaje", viaje);
        List<Gasto> gastos = q.getResultList();

        return gastos;
    }
}

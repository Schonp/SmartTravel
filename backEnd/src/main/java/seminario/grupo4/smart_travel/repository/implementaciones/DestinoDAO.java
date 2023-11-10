package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Destino;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.interfaces.IDestinoDAO;

import java.util.List;

@Repository
public class DestinoDAO implements IDestinoDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional(readOnly = true)
    public List<Destino> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Destino> q = session.createQuery("FROM Destino", Destino.class);
        List<Destino> retorno = q.getResultList();

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Destino findById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Destino destino = session.get(Destino.class, id);

        return destino;
    }

    @Override
    @Transactional
    public void save(Destino destino) {
        Session session = entityManager.unwrap(Session.class);

        session.persist(destino);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Session session = entityManager.unwrap(Session.class);

        Query<Gasto> query = session.createQuery("delete from Destino where id=:idDestino");
        query.setParameter("idDestino",id);
        query.executeUpdate();
    }

    @Override
    public List<Destino> findByViaje(Viaje viaje) {
        Session session = entityManager.unwrap(Session.class);

        Query<Destino> q = session.createQuery("FROM Destino where viaje=:viaje", Destino.class);
        q.setParameter("viaje", viaje);
        List<Destino> retorno = q.getResultList();

        return retorno;
    }
}

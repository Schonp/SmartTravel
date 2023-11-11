package seminario.grupo4.smart_travel.repository.implementaciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seminario.grupo4.smart_travel.model.entity.Documento;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

@Repository
public class DocumentoDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Documento> getDocxViaje(Viaje viaje){
        Session session = entityManager.unwrap(Session.class);

        Query<Documento> q = session.createQuery("FROM Documento where viaje=:idViaje", Documento.class);
        q.setParameter("idViaje", viaje);

        List<Documento> retorno = q.getResultList();

        return retorno;
    }
}

package seminario.grupo4.smart_travel.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import seminario.grupo4.smart_travel.model.entity.Documento;

public interface IDocumentoDAO extends JpaRepository<Documento, Long> {
}

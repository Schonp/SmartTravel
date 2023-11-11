package seminario.grupo4.smart_travel.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import seminario.grupo4.smart_travel.model.dto.DocumentosDTO;
import seminario.grupo4.smart_travel.model.entity.Documento;
import seminario.grupo4.smart_travel.model.entity.Viaje;

import java.util.List;

public interface IUploadFilesService {
    String handleFileUpload(MultipartFile file, Viaje viaje, String tipo) throws Exception;
    Documento getById(Long id);
    void delete(Documento documento);
    List<DocumentosDTO> getByViaje(Viaje viaje);
}

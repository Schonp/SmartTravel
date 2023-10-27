package seminario.grupo4.smart_travel.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import seminario.grupo4.smart_travel.model.entity.Documento;
import seminario.grupo4.smart_travel.model.entity.Viaje;

public interface IUploadFilesService {
    String handleFileUpload(MultipartFile file, Viaje viaje) throws Exception;
    Documento getById(Long id);
    void delete(Documento documento);
}

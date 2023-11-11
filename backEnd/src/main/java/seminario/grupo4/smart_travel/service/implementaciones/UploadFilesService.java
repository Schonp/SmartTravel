package seminario.grupo4.smart_travel.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminario.grupo4.smart_travel.model.dto.DocumentosDTO;
import seminario.grupo4.smart_travel.model.entity.Documento;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.repository.implementaciones.DocumentoDAO;
import seminario.grupo4.smart_travel.repository.interfaces.IDocumentoDAO;
import seminario.grupo4.smart_travel.service.interfaces.IUploadFilesService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UploadFilesService implements IUploadFilesService {
    @Autowired
    private IDocumentoDAO documentoDAO;
    @Autowired
    private DocumentoDAO documentoDAO2;


    @Override
    public String handleFileUpload(MultipartFile file, Viaje viaje, String tipo) throws Exception {
        try{
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            if(!fileOriginalName.endsWith(".pdf")){
                throw new Exception("El archivo no es un pdf");
            }

            Documento documento = new Documento(fileOriginalName, bytes, tipo, viaje);

            documentoDAO.save(documento);

            return "Se guardo exitosamente";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Documento getById(Long id) {
        Optional<Documento> doc = documentoDAO.findById(id);

        return doc.orElse(null);
    }

    @Override
    public void delete(Documento documento) {
        documentoDAO.delete(documento);
    }

    @Override
    public List<DocumentosDTO> getByViaje(Viaje viaje) {
        List<DocumentosDTO> documentos = new ArrayList<>();
        DocumentosDTO aux = new DocumentosDTO();


        for (Documento doc: documentoDAO2.getDocxViaje(viaje)) {
            aux.setId(doc.getId());
            aux.setNombreDocumento(doc.getNombreDocumento());
            aux.setTipo(doc.getTipo());
            aux.setIdViaje(doc.getViaje().getId());

            documentos.add(aux);
        }

        return documentos;
    }


}

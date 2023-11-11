package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seminario.grupo4.smart_travel.model.entity.Documento;
import seminario.grupo4.smart_travel.service.interfaces.IUploadFilesService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/documento")
public class DocumentoController {
    @Autowired
    IUploadFilesService uploadFilesService;
    @Autowired
    IViajeService viajeService;

    @PostMapping("/{idViaje}/{tipo}")
    private ResponseEntity<String> uploadPic(@PathVariable Long idViaje, @PathVariable String tipo, @RequestParam MultipartFile file) throws Exception{

        if(viajeService.findById(idViaje) == null)
            return new ResponseEntity<>("El viaje con id " + idViaje + " no existe", null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(uploadFilesService.handleFileUpload(file, viajeService.findById(idViaje), tipo), null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumento(@PathVariable Long id) {
        Documento documento = uploadFilesService.getById(id);

        if(documento == null)
            return new ResponseEntity<>("El documento con id " + id + " no existe", null, HttpStatus.NOT_FOUND);


        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(documento.getDatosDocumento());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocumento(@PathVariable Long id) {
        Documento documento = uploadFilesService.getById(id);

        if(documento == null)
            return new ResponseEntity<>("El documento con id " + id + " no existe", null, HttpStatus.NOT_FOUND);

        uploadFilesService.delete(documento);

        return new ResponseEntity<>("El documento con id " + id + " fue eliminado", null, HttpStatus.OK);
    }
}

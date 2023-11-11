package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.EncuestaDTO;
import seminario.grupo4.smart_travel.model.entity.Encuesta;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.service.interfaces.IEncuestasService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/encuestas"})
public class EncuestasControllers {
    @Autowired
    private IEncuestasService encuestasService;
    @Autowired
    private IViajeService viajeService;
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(encuestasService.getAll(), null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Encuesta encuesta = encuestasService.getById(id);

        if(encuesta == null)
            return new ResponseEntity<>("La encuesta con id " + id + " no existe", null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(encuesta, null, HttpStatus.OK);
    }
    @GetMapping("/viaje/{idViaje}")
    public ResponseEntity<?> getByViaje(@PathVariable Long idViaje){
        Viaje viaje = viajeService.findById(idViaje);

        if(viaje == null)
            return new ResponseEntity<>("El viaje con id " + idViaje + " no existe", null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(encuestasService.getByViaje(viaje), null, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> hacerEncuesta(@RequestBody EncuestaDTO encuestaDTO){
        Viaje viaje = viajeService.findById(encuestaDTO.getViajeId());

        if (viaje == null)
            return new ResponseEntity<>("El viaje con id no existe", null, HttpStatus.NOT_FOUND);

        encuestasService.saveEncuesta(parseEntity(encuestaDTO));

        return new ResponseEntity<>(encuestaDTO, null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEncuesta(@PathVariable Long id){
        Encuesta encuesta = encuestasService.getById(id);

        if(encuesta == null)
            return new ResponseEntity<>("La encuesta con id " + id + " no existe", null, HttpStatus.NOT_FOUND);

        encuestasService.deleteEncusta(encuesta);

        return new ResponseEntity<>("La encuesta con id " + id + " fue eliminada", null, HttpStatus.OK);
    }

    // PARSE DTO A ENTITY

    private Encuesta parseEntity(EncuestaDTO encuestaDTO){
        Encuesta e = new Encuesta();

        e.setUrl(encuestaDTO.getUrl());
        e.setFomsId(encuestaDTO.getFomsId());
        e.setViaje(viajeService.findById(encuestaDTO.getViajeId()));

        return e;
    }
}

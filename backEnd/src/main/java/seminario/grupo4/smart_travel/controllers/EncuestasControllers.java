package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import seminario.grupo4.smart_travel.model.dto.EncuestaDTO;
import seminario.grupo4.smart_travel.model.entity.Encuesta;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.service.interfaces.IEncuestasService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/encuestas"})
public class EncuestasControllers {
    @Autowired
    private IEncuestasService encuestasService;
    @Autowired
    private IViajeService viajeService;
    private final String url = "http://localhost:8000/otro";
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<EncuestaDTO> dtos = new ArrayList<>();

        for (Encuesta e: encuestasService.getAll()){
            dtos.add(parseDto(e));
        }

        return new ResponseEntity<>(dtos, null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Encuesta encuesta = encuestasService.getById(id);

        if(encuesta == null)
            return new ResponseEntity<>("La encuesta con id " + id + " no existe", null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(parseDto(encuesta), null, HttpStatus.OK);
    }

    @GetMapping("/viaje/{idViaje}")
    public ResponseEntity<?> getByViaje(@PathVariable Long idViaje){
        Viaje viaje = viajeService.findById(idViaje);
        List<EncuestaDTO> dtos = new ArrayList<>();


        if(viaje == null)
            return new ResponseEntity<>("El viaje con id " + idViaje + " no existe", null, HttpStatus.NOT_FOUND);

        for (Encuesta e: encuestasService.getByViaje(viaje)){
            dtos.add(parseDto(e));
        }

        return new ResponseEntity<>(dtos, null, HttpStatus.OK);
    }

    @GetMapping("rtas/{idForms}")
    public ResponseEntity<?> buscarRtas(@PathVariable String idForms){
        RestTemplate restTemplate = new RestTemplate();

        String urlRtas = url+"/rtas/"+idForms;

        String response = restTemplate.getForObject(urlRtas, String.class);

        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @GetMapping("url/{idForms}")
    public ResponseEntity<?> buscarUrl(@PathVariable String idForms){
        RestTemplate restTemplate = new RestTemplate();

        String urlRtas = url+"/url/"+idForms;

        String response = restTemplate.getForObject(urlRtas, String.class);

        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @PostMapping("/hacerEncuestaPy/{idViaje}/")
    public ResponseEntity<?> hacerEncuestaPy(@PathVariable Long idViaje, @RequestParam String pregunta, @RequestParam String posiblesRespuestas){
        Viaje viaje = viajeService.findById(idViaje);

        if (viaje == null)
            return new ResponseEntity<>("El viaje con id no existe", null, HttpStatus.NOT_FOUND);

        RestTemplate restTemplate = new RestTemplate();

        String urlcrearEncuesta = url+"/crear/"+ viaje.getNombreViaje() +"/" + "?pregunta="+pregunta+"&posiblesRespuestas="+posiblesRespuestas;

        String idForm = restTemplate.getForObject(urlcrearEncuesta, String.class);

        String urldeEncuesta = url+"/url/"+idForm;

        String urlForm = restTemplate.getForObject(urldeEncuesta, String.class);

        EncuestaDTO encuestaDTO = new EncuestaDTO(idForm, pregunta, urlForm, idViaje);

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

    @GetMapping("/ping")
    public String ping(){
        RestTemplate restTemplate = new RestTemplate();

        String urlPing = url+"/ping";

        return restTemplate.getForObject(urlPing, String.class);
    }


    // PARSE DTO A ENTITY

    private Encuesta parseEntity(EncuestaDTO encuestaDTO){
        Encuesta e = new Encuesta();
        if(encuestaDTO.getEncuestaId()!= null){
            e.setId(encuestaDTO.getEncuestaId());
        }
        e.setPregunta(encuestaDTO.getPregunta());
        e.setUrl(encuestaDTO.getUrl());
        e.setFomsId(encuestaDTO.getFomsId());
        e.setViaje(viajeService.findById(encuestaDTO.getViajeId()));

        return e;
    }

    private EncuestaDTO parseDto(Encuesta e) {
        EncuestaDTO dto = new EncuestaDTO();

        dto.setEncuestaId(e.getId());
        dto.setUrl(e.getUrl());
        dto.setPregunta(e.getPregunta());
        dto.setFomsId(e.getFomsId());
        dto.setViajeId(e.getViaje().getId());

        return dto;
    }
}

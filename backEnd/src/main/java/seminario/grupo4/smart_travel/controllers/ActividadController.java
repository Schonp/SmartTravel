package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.ActividadesDTO;
import seminario.grupo4.smart_travel.model.entity.Actividades;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.service.interfaces.IActividadesService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/actividad")
public class ActividadController {
    @Autowired
    private IActividadesService actividadService;
    @Autowired
    private IViajeService viajeService;

    @GetMapping({""})
    public List<ActividadesDTO> obtenerTodasLasActividades() {
        List<ActividadesDTO> actividadesDTOList = new ArrayList<>();

        for (Actividades a : actividadService.findAll()) {
            actividadesDTOList.add(parseDTO(a));
        }

        return actividadesDTOList;
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<?> obtenerActividadPorID(@PathVariable Long id){
        Actividades actividades = actividadService.findById(id);

        if (actividades == null){
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ninguna actividad con el id ingresado." + id, null, 404);
        } else {
            return new ResponseEntity<>(parseDTO(actividades), null, 200);
        }
    }

    @GetMapping("/viaje/{id}")
    public ResponseEntity<?> obtenesActividadPorViaje(@PathVariable Long id){
        Viaje viaje = viajeService.findById(id);

        if (viaje == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + id, null, 404);

        List<ActividadesDTO> actividadesDTOList = new ArrayList<>();

        for (Actividades a : actividadService.findByViaje(viaje)){
            actividadesDTOList.add(parseDTO(a));
        }

        return new ResponseEntity<>(actividadesDTOList, null, 200);
    }

    @PostMapping("")
    public ResponseEntity<?> addActividad(@RequestBody ActividadesDTO actividadesDTO){
        if (viajeService.findById(actividadesDTO.getViajeId()) != null){
            Actividades actividades = parseEntity(actividadesDTO);

            actividadService.save(actividades);

            return new ResponseEntity<>(parseDTO(actividades), null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + actividadesDTO.getViajeId(), null, 404);
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateActividad(@PathVariable long id, @RequestBody ActividadesDTO actividadesDTO){
        if (actividadService.findById(id) != null){
            Actividades actividades = parseEntity(actividadesDTO);

            actividadService.update(id, actividades);

            return new ResponseEntity<>(parseDTO(actividades), null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ninguna actividad con el id ingresado." + id, null, 404);
        }
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> deleteActividad(@PathVariable long id){
        if (actividadService.findById(id) != null){
            actividadService.deleteById(id);

            return new ResponseEntity<>("Se ha eliminado la actividad con el id ingresado." + id, null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ninguna actividad con el id ingresado." + id, null, 404);
        }
    }

    // PARSER METHODS
    private ActividadesDTO parseDTO(Actividades actividades){
        ActividadesDTO dto = new ActividadesDTO();

        dto.setNombreActividad(actividades.getNombreActividad());
        dto.setLugar(actividades.getLugar());
        dto.setFecha(actividades.getFecha());
        dto.setViajeId(actividades.getViaje().getId());

        return dto;
    }

    private Actividades parseEntity(ActividadesDTO dto){
        Actividades actividades = new Actividades();

        actividades.setNombreActividad(dto.getNombreActividad());
        actividades.setLugar(dto.getLugar());
        actividades.setFecha(dto.getFecha());
        actividades.setViaje(viajeService.findById(dto.getViajeId()));

        return actividades;
    }
}

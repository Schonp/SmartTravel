package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.ViajeDTO;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.service.implementaciones.UsuarioService;
import seminario.grupo4.smart_travel.service.implementaciones.ViajeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/viaje")
public class ViajesController {

    @Autowired
    private ViajeService viajeService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public List<ViajeDTO> getAll() {
        List<ViajeDTO> viajesDTO = new ArrayList<>();

        for (Viaje v : viajeService.findAll()){
            viajesDTO.add(parseDTO(v));
        }

        return viajesDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable long id){
        Viaje viaje = viajeService.findById(id);

        if (viaje == null) {
            String mensaje = "El viaje con id " + id + " no existe";
            return new ResponseEntity(mensaje, null, 404);
        }

        return new ResponseEntity(parseDTO(viaje), null, 200);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ViajeDTO viajeDTO){
        Viaje viaje = parseEntity(viajeDTO);

        viajeService.save(viaje);

        return new ResponseEntity(parseDTO(viaje), null, 201);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ViajeDTO viajeDTO){
        Viaje viajeBD = viajeService.findById(id);

        if(viajeBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + id,null,404);

        Viaje viaje = parseEntity(viajeDTO);

        viajeService.update(id, viaje);

        return new ResponseEntity<>(viajeDTO,null,200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        Viaje viajeBD = viajeService.findById(id);

        if (viajeBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado. " + id,null,404);

        viajeService.deleteById(id);

        return new ResponseEntity<>("Viaje eliminado exitosamente. " + id,null,200);
    }

    // PARSE METHODS

    private ViajeDTO parseDTO(Viaje v) {
        ViajeDTO viajeDTO = new ViajeDTO();

        viajeDTO.setNombreViaje(v.getNombreViaje());
        viajeDTO.setFechaInicio(v.getFechaInicio());
        viajeDTO.setFechaFin(v.getFechaFin());
        if(v.getUsuario() != null)
            viajeDTO.setIdUsuario(v.getUsuario().getId());

        return viajeDTO;
    }

    private Viaje parseEntity(ViajeDTO viajeDTO){
        Viaje viaje = new Viaje();

        viaje.setNombreViaje(viajeDTO.getNombreViaje());
        viaje.setFechaInicio(viajeDTO.getFechaInicio());
        viaje.setFechaFin(viajeDTO.getFechaFin());
        if(viajeDTO.getIdUsuario() != 0)
            viaje.setUsuario(usuarioService.findById(viajeDTO.getIdUsuario()));

        return viaje;
    }
}

package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import seminario.grupo4.smart_travel.model.dto.DestinoDTO;
import seminario.grupo4.smart_travel.model.entity.Destino;
import seminario.grupo4.smart_travel.service.interfaces.IDestinoService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/destino")
public class DestinoController {
    @Autowired
    private IDestinoService destinoService;
    @Autowired
    private IViajeService viajeService;

    @GetMapping({""})
    public List<DestinoDTO> obtenerTodosLosDestinos() {
        List<DestinoDTO> destinoDTOList = new ArrayList<>();

        for (Destino d : destinoService.findAll()) {
            destinoDTOList.add(parseDTO(d));
        }

        return destinoDTOList;
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> obtenerDestinoPorID(@PathVariable Long id){
        Destino destino = destinoService.findById(id);

        if (destino == null){
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún destino con el id ingresado." + id, null, 404);
        } else {
            return new ResponseEntity<>(parseDTO(destino), null, 200);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addDestino(@RequestBody DestinoDTO destinoDTO){
        if (viajeService.findById(destinoDTO.getViajeId()) != null){
            Destino destino = parseEntity(destinoDTO);

            destinoService.save(destino);

            return new ResponseEntity<>(parseDTO(destino), null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + destinoDTO.getViajeId(), null, 404);
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateDestino(@PathVariable Long id, @RequestBody DestinoDTO destinoDTO){
        if (destinoService.findById(id) != null){
            Destino destino = parseEntity(destinoDTO);

            destinoService.update(id, destino);

            return new ResponseEntity<>(parseDTO(destino), null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún destino con el id ingresado." + id, null, 404);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> eliminarDestino(@PathVariable Long id){
        if (destinoService.findById(id) != null){
            destinoService.deleteById(id);

            return new ResponseEntity<>("Se ha eliminado el destino con el id ingresado." + id, null, 200);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún destino con el id ingresado." + id, null, 404);
        }
    }

    // PARSE DTO
    private DestinoDTO parseDTO(Destino destino) {
        DestinoDTO destinoDTO = new DestinoDTO();

        destinoDTO.setCiudadDestino(destino.getCiudadDestino());
        destinoDTO.setFechaInicio(destino.getFechaInicio());
        destinoDTO.setFechaFin(destino.getFechaFin());
        destinoDTO.setViajeId(destino.getViaje().getId());

        return destinoDTO;
    }

    private Destino parseEntity(DestinoDTO dto){
        Destino destino = new Destino();

        destino.setCiudadDestino(dto.getCiudadDestino());
        destino.setFechaInicio(dto.getFechaInicio());
        destino.setFechaFin(dto.getFechaFin());
        destino.setViaje(viajeService.findById(dto.getViajeId()));

        return destino;
    }
}

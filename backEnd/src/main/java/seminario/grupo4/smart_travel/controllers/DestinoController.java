package seminario.grupo4.smart_travel.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import seminario.grupo4.smart_travel.model.dto.DestinoDTO;
import seminario.grupo4.smart_travel.model.entity.Destino;
import seminario.grupo4.smart_travel.model.entity.Viaje;
import seminario.grupo4.smart_travel.service.interfaces.IDestinoService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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

    @GetMapping("/viaje/{id}")
    public ResponseEntity<?> obtenesDestinoPorViaje(@PathVariable Long id){
        Viaje viaje = viajeService.findById(id);

        if (viaje == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + id, null, 404);

        List<DestinoDTO> destinoDTOList = new ArrayList<>();

        for (Destino d : destinoService.findByViaje(viaje)){
            destinoDTOList.add(parseDTO(d));
        }

        return new ResponseEntity<>(destinoDTOList, null, 200);
    }

    @GetMapping("destinoFinal/{id}")
    public ResponseEntity<?> obtenerDestinoFinal(@PathVariable Long id){
        Viaje viaje = viajeService.findById(id);

        if (viaje == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + id, null, 404);

        String retorno = "";

        List<Destino> destinos = destinoService.findByViaje(viaje);

        if(destinos.size() == 1)
            retorno = destinos.get(0).getCiudadDestino();
        if(destinos.size() == 2){
            for (Destino d : destinos){
                retorno = retorno + d.getCiudadDestino() + " - ";
            }

            retorno = retorno.substring(0, retorno.length() - 3);
        }
        if (destinos.size() > 2){
            retorno = destinos.get(0).getCiudadDestino() + " + " + (destinos.size() - 1);
        }

        return new ResponseEntity<>(retorno, null, 200);
    }

    @GetMapping("diasFinal/{id}")
    public ResponseEntity<?> obtenerDiasViaje(@PathVariable Long id){
        Viaje viaje = viajeService.findById(id);

        if (viaje == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún viaje con el id ingresado." + id, null, 404);


        List<Destino> destinos = destinoService.findByViaje(viaje);

        Date fechaInicio = destinos.get(0).getFechaInicio();
        Date fechaFin = destinos.get(0).getFechaFin();

        for (Destino d : destinos){
            if (fechaInicio.compareTo(d.getFechaInicio()) > 0){
                fechaInicio = d.getFechaInicio();
            }
            if(fechaFin.compareTo(d.getFechaFin()) < 0){
                fechaFin = d.getFechaFin();
            }
        }

        return new ResponseEntity<>(new FechaTot(fechaInicio, fechaFin), null, 200);
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

        destinoDTO.setDestinoId(destino.getId());
        destinoDTO.setCiudadDestino(destino.getCiudadDestino());
        destinoDTO.setFechaInicio(destino.getFechaInicio());
        destinoDTO.setFechaFin(destino.getFechaFin());
        destinoDTO.setViajeId(destino.getViaje().getId());

        return destinoDTO;
    }

    private Destino parseEntity(DestinoDTO dto){
        Destino destino = new Destino();
        if(dto.getDestinoId()!= null){
            destino.setId(dto.getDestinoId());
        }
        destino.setCiudadDestino(dto.getCiudadDestino());
        destino.setFechaInicio(dto.getFechaInicio());
        destino.setFechaFin(dto.getFechaFin());
        destino.setViaje(viajeService.findById(dto.getViajeId()));

        return destino;
    }

    // FECHA DE VUELTA
    @Getter
    @Setter
    private class FechaTot{
        private Date fechaInicio;
        private Date fechaFin;

        public FechaTot() {
        }

        public FechaTot(Date fechaInicio, Date fechaFin) {
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        @Override
        public String toString() {
            return "FechaTot{" +
                    "fechaInicio=" + fechaInicio +
                    ", fechaFin=" + fechaFin +
                    '}';
        }
    }
}

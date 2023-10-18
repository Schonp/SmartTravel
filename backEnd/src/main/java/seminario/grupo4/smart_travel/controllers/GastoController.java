package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.GastoDTO;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.service.interfaces.IGastoService;
import seminario.grupo4.smart_travel.service.interfaces.IMiembroService;
import seminario.grupo4.smart_travel.service.interfaces.IViajeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gasto")
public class GastoController {
    @Autowired
    private IGastoService gastoService;
    @Autowired
    private IViajeService viajeService;
    @Autowired
    private IMiembroService miembroService;

    @GetMapping({""})
    public List<GastoDTO> obtenerTodosLosGastos(){
        List<GastoDTO> gastoDTOList = new ArrayList<>();

        for(Gasto g:gastoService.findAll()){
            gastoDTOList.add(parseDTO(g));
        }
        return gastoDTOList;
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<?> obtenerGastoPorID(@PathVariable Long id) {
        Gasto gasto = gastoService.findById(id);

        if (gasto == null) {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + id, null, 404);
        } else {
            return new ResponseEntity<>(parseDTO(gasto), null, 200);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> guardarGasto(@RequestBody GastoDTO gasto){
        gastoService.save(parseEntity(gasto));
        return new ResponseEntity<>(gasto,null,201);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO){
        Gasto gastoBD = gastoService.findById(id);

        if(gastoBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + id,null,404);

        Gasto gasto = parseEntity(gastoDTO);

        gastoService.update(id, gasto);

        return new ResponseEntity<>(gastoDTO,null,200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable Long id) {
        Gasto gastoDB = gastoService.findById(id);

        if (gastoDB == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado. " + id,null,404);

        gastoService.deleteById(id);

        return new ResponseEntity<>("Gasto eliminado exitosamente. " + id,null,204);
    }






    private GastoDTO parseDTO(Gasto gasto){
        GastoDTO gastoDTO = new GastoDTO();

        gastoDTO.setNombreGasto(gasto.getNombreGasto());
        gastoDTO.setMonto(gasto.getMonto());
        gastoDTO.setIdComprador(gasto.getComprador().getId());
        gastoDTO.setIdViaje(gasto.getViaje().getId());

        return gastoDTO;
    }

    private Gasto  parseEntity(GastoDTO gastoDTO){
        Gasto gasto = new Gasto();

        gasto.setViaje(viajeService.findById(gastoDTO.getIdViaje()));
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setComprador(miembroService.findById(gastoDTO.getIdComprador()));
        gasto.setNombreGasto(gastoDTO.getNombreGasto());

        return gasto;
    }
}

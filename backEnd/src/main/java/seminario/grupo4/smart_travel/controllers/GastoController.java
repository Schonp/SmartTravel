package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.GastoDTO;
import seminario.grupo4.smart_travel.model.entity.Gasto;
import seminario.grupo4.smart_travel.model.entity.Miembro;
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
    public List<GastoDTO> obtenerTodosLosGastos() {
        List<GastoDTO> gastoDTOList = new ArrayList<>();

        for (Gasto g : gastoService.findAll()) {
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
    public ResponseEntity<?> guardarGasto(@RequestBody GastoDTO gasto) {

        if (miembroService.findById(gasto.getIdComprador()) != null) {

            gastoService.save(parseEntity(gasto));

            comprar(gasto); // -- AHORA SE ACTUALIZA LOS BALANCES DE LOS MIEMBROS DEL VIAJE //

            return new ResponseEntity<>(gasto, null, 201);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + gasto.getIdComprador(), null, 404);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO) {
        Gasto gastoBD = gastoService.findById(id);

        if (gastoBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + id, null, 404);

        Gasto gasto = parseEntity(gastoDTO);

        actualizarGasto(gastoDTO, gastoBD);

        gastoService.update(id, gasto); // se me cambia el monto originak

        return new ResponseEntity<>(gastoDTO, null, 200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable Long id) {
        Gasto gastoDB = gastoService.findById(id);

        if (gastoDB == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado. " + id, null, 404);

        borrarGasto(parseDTO(gastoDB));

        gastoService.deleteById(id);


        return new ResponseEntity<>("Gasto eliminado exitosamente. " + id, null, 200);
    }

    // TODO PAGAR GASTO ENTRE X e Y "MIEMBRO" //
    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> pagarCuentas(@PathVariable Long idGasto, @RequestParam Long idPagador) {
        Gasto gasto = gastoService.findById(idGasto);

        if (gasto == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + idGasto, null, 404);

        Miembro pagador = miembroService.findById(idPagador);

        if (pagador == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + idPagador, null, 404);

        double monto = gasto.getMonto();


        return null;
    }

    // PARSE METHODS
    private GastoDTO parseDTO(Gasto gasto) {
        GastoDTO gastoDTO = new GastoDTO();

        gastoDTO.setNombreGasto(gasto.getNombreGasto());
        gastoDTO.setMonto(gasto.getMonto());
        if (gasto.getComprador() != null)
            gastoDTO.setIdComprador(gasto.getComprador().getId());
        if (gasto.getViaje() != null)
            gastoDTO.setIdViaje(gasto.getViaje().getId());

        return gastoDTO;
    }

    private Gasto parseEntity(GastoDTO gastoDTO) {
        Gasto gasto = new Gasto();

        if (gastoDTO.getIdViaje() != 0)
            gasto.setViaje(viajeService.findById(gastoDTO.getIdViaje()));
        gasto.setMonto(gastoDTO.getMonto());
        if (gastoDTO.getIdComprador() != 0)
            gasto.setComprador(miembroService.findById(gastoDTO.getIdComprador()));
        gasto.setNombreGasto(gastoDTO.getNombreGasto());

        return gasto;
    }

    // -- METODOS PARA ACTUALIZAR LOS BALANCES DE LOS MIEMBROS DEL VIAJE //

    private void comprar(GastoDTO gasto) {
        double monto = gasto.getMonto();
        Long idComprador = gasto.getIdComprador();
        long idViaje = gasto.getIdViaje();

        List<Miembro> miembros = miembroService.findbyIdVieje(idViaje);
        double montoxpersona = monto / miembros.size();
        montoxpersona = Math.round(montoxpersona * 100.0) / 100.0;

        for (Miembro deudor : miembros) {
            if (deudor.getId() != idComprador && deudor.getViaje().getId() == idViaje) {
                deudor.setBalance(deudor.getBalance() + montoxpersona);
                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(idComprador);
        comprador.setBalance(comprador.getBalance() - montoxpersona);
        miembroService.update(comprador.getId(), comprador);
    }

    // TODO no se puede cambiar de viaje el gasto //
    private void actualizarGasto(GastoDTO gastoDTO, Gasto gastoBD) {
        double montoOrginial = gastoBD.getMonto();
        double montoNuevo = gastoDTO.getMonto();


        if (montoNuevo == montoOrginial) {
            return;
        }

        double diferencia = montoNuevo - montoOrginial;

        List<Miembro> miembros = miembroService.findbyIdVieje(gastoBD.getViaje().getId());

        double montoxpersona = diferencia / miembros.size();
        montoxpersona = Math.round(montoxpersona * 100.0) / 100.0;

        for (Miembro deudor : miembros) {
            if (deudor.getId() != gastoDTO.getIdComprador() && deudor.getViaje().getId() == gastoDTO.getIdViaje()) {
                deudor.setBalance(deudor.getBalance() + montoxpersona);
                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(gastoDTO.getIdComprador());
        comprador.setBalance(comprador.getBalance() - montoxpersona);
        miembroService.update(comprador.getId(), comprador);
    }

    private void borrarGasto(GastoDTO gastoDTO) {
        double monto = gastoDTO.getMonto();
        Long idComprador = gastoDTO.getIdComprador();
        long idViaje = gastoDTO.getIdViaje();

        List<Miembro> miembros = miembroService.findbyIdVieje(idViaje);

        double montoxpersona = monto / miembros.size();
        montoxpersona = Math.round(montoxpersona * 100.0) / 100.0;

        for (Miembro deudor : miembros) {
            if (deudor.getId() != idComprador && deudor.getViaje().getId() == idViaje) {
                deudor.setBalance(deudor.getBalance() - montoxpersona);
                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(idComprador);
        comprador.setBalance(comprador.getBalance() + montoxpersona);
        miembroService.update(comprador.getId(), comprador);
    }

    private double montoXPersoona(List<Miembro> miembros, double monto) {
        double montoxpersona = monto / miembros.size();
        montoxpersona = Math.round(montoxpersona * 100.0) / 100.0;

        return montoxpersona;
    }
}
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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

// TODO REFACTOR DE LOS METODOS NO SE ENTIENDE NADA

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

    // TODO CORRECION: AGREGAR EL GASTO A LA LISTA DEL MIEMRBO //
    @PostMapping("")
    public ResponseEntity<?> guardarGasto(@RequestBody GastoDTO gastoDTO) {

        if (miembroService.findById(gastoDTO.getIdComprador()) != null) {

            Gasto gasto = parseEntity(gastoDTO);

            gastoService.save(gasto);

            comprar(gasto);

            return new ResponseEntity<>(gastoDTO, null, 201);
        } else {
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + gastoDTO.getIdComprador(), null, 404);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO) {
        Gasto gastoBD = gastoService.findById(id);

        if (gastoBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + id, null, 404);

        Gasto gasto = parseEntity(gastoDTO);

        gastoService.update(id, gasto); // se me cambia el monto originak

        actualizarGasto(gasto, gastoBD);

        return new ResponseEntity<>(gastoDTO, null, 200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable Long id) {
        Gasto gastoDB = gastoService.findById(id);

        if (gastoDB == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado. " + id, null, 404);

        borrarGasto(gastoDB);

        gastoService.deleteById(id);


        return new ResponseEntity<>("Gasto eliminado exitosamente. " + id, null, 200);
    }

    @PutMapping("/pagar")
    public ResponseEntity<?> pagarCuentas(@RequestParam Long idGasto, @RequestParam Long idPagador) {
        Gasto gasto = gastoService.findById(idGasto);

        if (gasto == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún gasto con el id ingresado." + idGasto, null, 404);

        Miembro pagador = miembroService.findById(idPagador);

        if (pagador == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + idPagador, null, 404);

        List<Miembro> miembros = miembroService.findbyIdVieje(gasto.getViaje().getId());
        double montoxpersona = montoXPersoona(miembros, gasto.getMonto());

        System.out.println("DUEDAS : " + pagador.getGastos());
        System.out.println("GASTO  : "+gasto);

        if (!pagador.getGastos().contains(gasto)){      // TODO no se porque salta a esto, no se acutaliza las listas
            return new ResponseEntity<>("Lo sentimos, el miembro no tiene este gasto." + idPagador, null, 404);
        }

        pagador.setBalance(pagador.getBalance() + montoxpersona);

        List<Gasto> gastos = pagador.getGastos();


        gastos.remove(gasto);   // NO SE ELIMINAAA
        pagador.setGastos(gastos);

        System.out.println("GASTOS UPDATE: "+pagador.getGastos());

        miembroService.update(pagador.getId(), pagador);

        Miembro comprador = gasto.getComprador();
        comprador.setBalance(comprador.getBalance() - montoxpersona);

        miembroService.update(comprador.getId(), comprador);

        return new ResponseEntity<>("Se pago el gasto entre "+ pagador.getNombre() + " y " + comprador.getNombre(), null, 200);
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

    private void comprar(Gasto gasto) {
        double monto = gasto.getMonto();
        Long idComprador = gasto.getComprador().getId();
        long idViaje = gasto.getViaje().getId();

        List<Miembro> miembros = miembroService.findbyIdVieje(idViaje);
        double montoxpersona = montoXPersoona(miembros, monto);


        for (Miembro deudor : miembros) {
            if (deudor.getId() != idComprador && deudor.getViaje().getId() == idViaje) {
                System.out.println(deudor.getNombre());

                deudor.setBalance(deudor.getBalance() + montoxpersona);

                List<Gasto> duedas = deudor.getGastos();

                duedas.add(gasto);

                deudor.setGastos(duedas);

                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(idComprador);

        comprador.setBalance(comprador.getBalance() - montoxpersona);

        miembroService.update(comprador.getId(), comprador);

        for (Miembro m: miembroService.findAll()) {
            System.out.println(m.getGastos());
        }
    }

    // TODO no se puede cambiar de viaje el gasto //
    private void actualizarGasto(Gasto gasto, Gasto gastoBD) {
        double montoOrginial = gastoBD.getMonto();
        double montoNuevo = gasto.getMonto();


        if (montoNuevo == montoOrginial) {
            return;
        }

        double diferencia = montoNuevo - montoOrginial;

        List<Miembro> miembros = miembroService.findbyIdVieje(gastoBD.getViaje().getId());

        double montoxpersona = montoXPersoona(miembros, diferencia);

        for (Miembro deudor : miembros) {
            if (deudor.getId() != gasto.getComprador().getId() && deudor.getViaje().getId() == gasto.getViaje().getId()) {
                deudor.setBalance(deudor.getBalance() + montoxpersona);

                List<Gasto> gastos = deudor.getGastos();
                gastos.set(gastos.indexOf(gastoBD), gasto);
                deudor.setGastos(gastos);

                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(gasto.getComprador().getId());
        comprador.setBalance(comprador.getBalance() - montoxpersona);
        miembroService.update(comprador.getId(), comprador);
    }

    private void borrarGasto(Gasto gasto) {
        double monto = gasto.getMonto();
        Long idComprador = gasto.getComprador().getId();
        long idViaje = gasto.getViaje().getId();

        List<Miembro> miembros = miembroService.findbyIdVieje(idViaje);

        double montoxpersona = montoXPersoona(miembros, monto);

        for (Miembro deudor : miembros) {
            if (deudor.getId() != idComprador && deudor.getViaje().getId() == idViaje) {
                deudor.setBalance(deudor.getBalance() - montoxpersona);

                List<Gasto> gastos = deudor.getGastos();
                gastos.remove(gasto);
                deudor.setGastos(gastos);

                miembroService.update(deudor.getId(), deudor);
            }
        }

        Miembro comprador = miembroService.findById(idComprador);

        comprador.setBalance(comprador.getBalance() + montoxpersona);

        miembroService.update(comprador.getId(), comprador);

        for (Miembro m: miembroService.findAll()) {
            System.out.println(m.getGastos());
        }
    }

    private double montoXPersoona(List<Miembro> miembros, double monto) {
        double montoxpersona = monto / miembros.size();
        montoxpersona = Math.round(montoxpersona * 100.0) / 100.0;

        return montoxpersona;
    }
}
package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminario.grupo4.smart_travel.model.dto.MiembroDTO;
import seminario.grupo4.smart_travel.model.entity.Miembro;
import seminario.grupo4.smart_travel.service.interfaces.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private IMiembroService miembroService;
    @Autowired
    private IViajeService viajeService;

    @GetMapping({""})
    public List<MiembroDTO> getAll(){
        List<MiembroDTO> usuarioDTOList = new ArrayList<>();

        for (Miembro u:miembroService.findAll()) {
            usuarioDTOList.add(parseDTO(u));
        }

        return usuarioDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Miembro miembro = miembroService.findById(id);

        if(miembro == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + id,null,404);

        return new ResponseEntity<>(parseDTO(miembro),null,200);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody MiembroDTO miembroDTO){
        miembroService.save(parseEntity(miembroDTO));
        return new ResponseEntity<>(miembroDTO,null,201);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MiembroDTO miembroDTO){
        Miembro miembroBD = miembroService.findById(id);

        if(miembroBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado." + id,null,404);

        Miembro miembro = parseEntity(miembroDTO);

        miembroService.update(id, miembro);

        return new ResponseEntity<>(miembroDTO,null,200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Miembro miembroDB = miembroService.findById(id);

        if (miembroDB == null)  // TODO VER LOS PARSE DTOS PARA QUE FUNQUE TODO
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún miembro con el id ingresado. " + id,null,404);

        miembroService.deleteById(id);

        return new ResponseEntity<>("El miembro ha sido eliminado correctamente.",null,200);
    }

    // PARSER METHODS
    private MiembroDTO parseDTO(Miembro u) {
        MiembroDTO miembroDTO = new MiembroDTO();

        miembroDTO.setNombre(u.getNombre());
        miembroDTO.setEmail(u.getEmail());
        miembroDTO.setBalance(u.getBalance());
        if(u.getViaje() != null)
            miembroDTO.setIdViaje(u.getViaje().getId());

        return miembroDTO;
    }

    private Miembro parseEntity(MiembroDTO u) {
        Miembro miembro = new Miembro();

        miembro.setNombre(u.getNombre());
        miembro.setEmail(u.getEmail());
        miembro.setBalance(u.getBalance());
        if(u.getIdViaje() != 0) {
            miembro.setViaje(viajeService.findById(u.getIdViaje()));
        }

        return miembro;
    }


}

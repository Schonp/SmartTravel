package seminario.grupo4.smart_travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminario.grupo4.smart_travel.model.dto.UsuarioDTO;
import seminario.grupo4.smart_travel.model.entity.Usuario;
import seminario.grupo4.smart_travel.service.interfaces.IUsuarioService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping({""})
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for (Usuario u:usuarioService.findAll()) {
            usuarioDTOList.add(parseDTO(u));
        }

        return usuarioDTOList;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorID(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);

        if(usuario == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún usuario con el id ingresado." + id,null,404);

        return new ResponseEntity<>(parseDTO(usuario),null,200);
    }
    @PostMapping("")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
        return new ResponseEntity<>(parseDTO(usuario),null,201);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioBD = usuarioService.findById(id);

        if(usuarioBD == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún usuario con el id ingresado." + id,null,404);

        Usuario usuario = parseEntity(usuarioDTO);

        usuarioService.update(id, usuario);

        return new ResponseEntity<>(usuarioDTO,null,200);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        Usuario usuarioDB = usuarioService.findById(id);

        if (usuarioDB == null)
            return new ResponseEntity<>("Lo sentimos, no se ha encontrado ningún usuario con el id ingresado. " + id,null,404);

        usuarioService.deleteById(id);

        return new ResponseEntity<>("Usuario eliminado exitosamente. " + id,null,200);
    }

    @GetMapping("/{nombreUs}/{contraseña}")
    public ResponseEntity<?> checkLogIn (@PathVariable String nombreUs, @PathVariable String contraseña) {
        Usuario usuario = usuarioService.findUser(nombreUs,contraseña);

        if(usuario == null) {
            return new ResponseEntity<>("Lo sentimos",null,404);
        }

        return ResponseEntity.ok().body("Inicio de sesión exitoso");
    }

    // PARSE METHODS
    private UsuarioDTO parseDTO(Usuario usuario) {
        UsuarioDTO retorno = new UsuarioDTO();

        retorno.setUsuarioId(usuario.getId());
        retorno.setNombreUs(usuario.getNombreUs());
        retorno.setEmail(usuario.getEmail());;

        return retorno;
    }

    private Usuario parseEntity(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        if(usuarioDTO.getUsuarioId()!= null){
            usuario.setId(usuarioDTO.getUsuarioId());
        }
        usuario.setNombreUs(usuarioDTO.getNombreUs());
        usuario.setEmail(usuarioDTO.getEmail());

        return usuario;
    }

}

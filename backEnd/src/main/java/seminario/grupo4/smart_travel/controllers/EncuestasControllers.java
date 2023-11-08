package seminario.grupo4.smart_travel.controllers;

import org.python.util.PythonInterpreter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/encuestas"})
public class EncuestasControllers {
    @PostMapping("")
    public ResponseEntity<?> hacerEncuesta(){
        PythonInterpreter interpreter = new PythonInterpreter();
        //interpreter.exec("E:\\3 Seminario Integracion Profesional\\-- CODIGO --\\SmartTravel\\formsPython\\main.py");
        interpreter.exec("print('hola')");

        return null;
    }

}

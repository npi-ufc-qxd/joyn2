package br.ufc.npi.joyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {
	
	@RequestMapping(path="/cadastroUsuario")
    public String cadastroUsuario() {
        return "cadastroUsuario";
    }

}

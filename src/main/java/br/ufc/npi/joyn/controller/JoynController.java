package br.ufc.npi.joyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Usuario;

@Controller
@RequestMapping(path="/")
public class JoynController {
	
	@RequestMapping(path="")
	public ModelAndView loginUsuario() {
		ModelAndView model = new ModelAndView("formLoginUsuario");
		model.addObject("usuario", new Usuario());
		return model;
	}

}

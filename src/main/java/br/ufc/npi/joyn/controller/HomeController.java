package br.ufc.npi.joyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/")
public class HomeController {
	
	@RequestMapping(path="")
	public String home(){
		return "redirect:/usuario/logar";
	}

}

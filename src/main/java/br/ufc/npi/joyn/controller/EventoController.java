package br.ufc.npi.joyn.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.service.EventoService;

@Controller
@RequestMapping(path="/evento")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@GetMapping(path="/cadastrar")
	public ModelAndView cadastrarEventoFormulario(){
		ModelAndView model = new ModelAndView("formCadastroEvento");
		model.addObject("evento", new Evento());
		
		return model;
	}
	
	@PostMapping(path="/cadastrar")
	public String cadastrarEvento(@Valid Evento evento, BindingResult result){
		if (result.hasErrors()) return "formCadastroEvento";
		eventoService.cadastrarEvento(evento.getNome(), evento.getDescricao(), evento.getDataInicio(),
				evento.getDataFim(), evento.getVagas(), evento.getLocal());
		return "redirect:/formLoginUsuario";
	}
}

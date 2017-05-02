package br.ufc.npi.joyn.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.EventoService;

@Controller
@RequestMapping(path="/evento")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioController usuarioController;
	
	@GetMapping(path="/meus_eventos")
	public ModelAndView meusEventos(){
		ModelAndView model = new ModelAndView("meusEventos");
		Usuario logado = usuarioController.getUsuarioLogado();
		List<Evento> eventos = eventoService.getMeusEventos(logado.getId());
		model.addObject("eventos", eventos);		
		return model;
	}
	
	@PostMapping(path="/meus_eventos")
	public ModelAndView meusEventosPorNome(@RequestParam("nome") String nome){
		ModelAndView model = new ModelAndView("meusEventos");
		Usuario logado = usuarioController.getUsuarioLogado();
		List<Evento> eventos = eventoService.getMeusEventosPorNome(logado.getId(), nome.toUpperCase());		
		model.addObject("eventos", eventos);
		return model;
	}
	
	@GetMapping(path="/salvar")
	public ModelAndView salvarEventoFormulario(){
		ModelAndView model = new ModelAndView("formCadastroEvento");
		model.addObject("evento", new Evento());
		
		return model;
	}
	
	@PostMapping(path="/salvar")
	public String salvarEvento(@Valid Evento evento){
		/*if (evento.getNome().equals("")) {
			return "formCadastroEvento";
		}*/
		
		//if (result.hasErrors()) return "formCadastroEvento"; Binding result
		
		/*if (evento.getDataFim().before(evento.getDataInicio())) {
			return "formCadastroEvento";
		}*/
		
		Evento salvo = eventoService.salvarEvento(evento);
		return "redirect:/evento/" + salvo.getId();
	}
	
	@GetMapping(path="/{id}")
	public ModelAndView visualizarEvento(@PathVariable("id") Long id){
		Evento evento = eventoService.buscarEvento(id);
		
		ModelAndView model = new ModelAndView("detalhesEvento");
		model.addObject("evento", evento);
		
		return model;
	}
}

package br.ufc.npi.joyn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.EventoService;

@Controller
@RequestMapping(path="/evento")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioController usuarioController;
	
	@GetMapping(path="/salvar")
	public ModelAndView salvarEventoFormulario(){

		ModelAndView model = new ModelAndView("formCadastroEvento");
		model.addObject("evento", new Evento());
		
		return model;
	}
	
	@PostMapping(path="/salvar")
	public String salvarEvento(@Valid Evento evento, BindingResult result){
		
		Date data = new Date(); 
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		String data_atual = formatador.format(data);
		
		if (evento.getNome().equals("")) return "formCadastroEvento";
		
		if (result.hasErrors()) return "formCadastroEvento";
		
		if (evento.getDataFim().before(evento.getDataInicio())) return "formCadastroEvento";
		
		if (evento.getDataInicio().toString().compareTo(data_atual) < 0) return "formCadastroEvento";
		
		Evento salvo = eventoService.salvarEvento(evento);
		return "redirect:/evento/" + salvo.getId();
	}
	
	@GetMapping(path="/{id}")
	public ModelAndView visualizarEvento(@PathVariable("id") Long id){
		
		Evento evento = eventoService.buscarEvento(id);
		
		Usuario usuarioLogado = usuarioController.getUsuarioLogado();
		for (ParticipacaoEvento u : evento.getParticipantes()) {
			if(u.getPapel().equals("ORGANIZADOR")){
				if (u.getUsuario().equals(usuarioLogado)){
					// Alguma coisa
				}
			}
		} 
		
		ModelAndView model = new ModelAndView("detalhesEvento");
		model.addObject("evento", evento);
		
		return model;
	}
	
	@GetMapping(path="/editar={id}")
	public ModelAndView editarEvento(@PathVariable("id") Long id){
		Evento evento = eventoService.buscarEvento(id);
		ModelAndView model = new ModelAndView("formEditarEvento");
		model.addObject("evento", evento);
		return model;
	}
	
	@PostMapping(path="/editar")
	public String atualizar(Evento evento, BindingResult result){
		Date data = new Date(); 
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		String data_atual = formatador.format(data);
		
		if (evento.getNome().equals("")) return "formEditarEvento";
		
		if (result.hasErrors()) return "formEditarEvento";
		
		if (evento.getDataFim().before(evento.getDataInicio())) return "formEditarEvento";
		
		if (evento.getDataInicio().toString().compareTo(data_atual) < 0) return "formEditarEvento";
		
		Evento evento_salvo = eventoService.salvarEvento(evento);
		return "redirect:/evento/"+evento_salvo.getId();
	}
}

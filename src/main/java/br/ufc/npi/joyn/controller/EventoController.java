package br.ufc.npi.joyn.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Convite;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.ConviteService;
import br.ufc.npi.joyn.service.EventoService;
import br.ufc.npi.joyn.service.ParticipacaoEventoService;
import br.ufc.npi.joyn.service.UsuarioService;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Controller
@RequestMapping(path="/evento")
@ComponentScan("br.ufc.quixada.npi.service")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ParticipacaoEventoService participacaoEventoService;
	
	@Autowired
	ConviteService conviteService;
	
	@Autowired
	SendEmailService service;
	
	@GetMapping(path="/salvar")
	public ModelAndView salvarEventoFormulario(){
		ModelAndView model = new ModelAndView("formCadastroEvento");
		model.addObject("evento", new Evento());
		
		return model;
	}
	
	@PostMapping(path="/salvar")
	public String salvarEvento(@Valid Evento evento, BindingResult result){
		if (evento.getNome().equals("")) return "formCadastroEvento";
		
		if (result.hasErrors()) return "formCadastroEvento";
		
		if (evento.getDataFim().before(evento.getDataInicio())) return "formCadastroEvento";
		
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
	
	@PostMapping(path="/convidar")
	public String convidar(HttpServletRequest request, @RequestParam String email, @RequestParam Long id){
		Usuario usuario = usuarioService.getUsuario(email);
		Evento evento = eventoService.buscarEvento(id);
		
		if(usuario != null){
			ParticipacaoEvento pe = new ParticipacaoEvento(usuario, evento, Papel.ORGANIZADOR, true);
			participacaoEventoService.addParticipacaoEvento(pe);
			
		} else {
			conviteService.addConvite(new Convite(email, id));
			String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
			EmailBuilder emailBuilder = new EmailBuilder("Joyn",
					 "joyn@npi.com.br",
					 "Convite para organizar evento", 
					 email, 
					 "Você foi convidado para organizar o evento: " + evento.getNome() 
					  + "\n Faca seu cadastro em: " + baseUrl + "/usuario/novo");
			Email emailConvite = new Email(emailBuilder);
			service.sendEmail(emailConvite);
		}
		return "redirect:/evento/"+id;
	}
}

package br.ufc.npi.joyn.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Atividade;
import br.ufc.npi.joyn.model.Convite;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoAtividade;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.AtividadeService;
import br.ufc.npi.joyn.service.ConviteService;
import br.ufc.npi.joyn.service.EventoService;
import br.ufc.npi.joyn.service.ParticipacaoAtividadeService;
import br.ufc.npi.joyn.service.ParticipacaoEventoService;
import br.ufc.npi.joyn.service.UsuarioService;

@Controller
@RequestMapping(path="/evento")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ParticipacaoEventoService participacaoEventoService;
	
	@Autowired
	ParticipacaoAtividadeService participacaoAtividadeService;
	
	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	ConviteService conviteService;
		
	@GetMapping(path="/meus_eventos")
	public ModelAndView meusEventos(){
		ModelAndView model = new ModelAndView("meusEventos");
		Usuario logado = usuarioService.getUsuarioLogado();
		List<Evento> eventos = eventoService.getMeusEventos(logado.getId());
		model.addObject("eventos", eventos);		
		return model;
	}
	
	@PostMapping(path="/meus_eventos")
	public ModelAndView meusEventosPorNome(@RequestParam("nome") String nome){
		ModelAndView model = new ModelAndView("meusEventos");
		Usuario logado = usuarioService.getUsuarioLogado();
		List<Evento> eventos = eventoService.getMeusEventosPorNome(logado.getId(), nome.toUpperCase());		
		model.addObject("eventos", eventos);
		return model;
	}
	
	@GetMapping(path="/cadastrar")
	public ModelAndView salvarEventoFormulario(){

		ModelAndView model = new ModelAndView("formCadastroEvento");
		model.addObject("evento", new Evento());
		
		return model;
	}
	
	@PostMapping(path="/cadastrar")
	public String salvarEvento(@Valid Evento evento, @RequestParam("dataInicio") String dataInicioStr, @RequestParam("dataFim") String dataFimStr, BindingResult result) throws ParseException{

		if (!verificarFormulario(evento, result)) return "formCadastroEvento"; 

		Evento salvo = eventoService.salvarEvento(evento);
		
		Usuario usuario = usuarioService.getUsuarioLogado();
		
		if(usuario != null){
			ParticipacaoEvento pe = new ParticipacaoEvento(usuario, salvo, Papel.ORGANIZADOR, true);
			participacaoEventoService.addParticipacaoEvento(pe);
		}
		
		return "redirect:/evento/" + salvo.getId();
	}
	
	@GetMapping(path="/{id}")
	public ModelAndView visualizarEvento(@PathVariable("id") Long id){
		
		Usuario user = usuarioService.getUsuarioLogado();
		List<ParticipacaoEvento> organizadores = participacaoEventoService.organizadoresEvento(id);
		
		Evento evento = eventoService.buscarEvento(id);
		
		for (ParticipacaoEvento org : organizadores) {
			if (user.getId() == org.getUsuario().getId()) {
				if (org.getPapel() == Papel.ORGANIZADOR) {
					ModelAndView model = new ModelAndView("detalhesEvento");
					model.addObject("evento", evento);
					model.addObject("usuario", user);
					return model;
				}
			}
		}
		return meusEventos();
	}

	@GetMapping(path="/editar/{id}")
	public ModelAndView editarEvento(@PathVariable("id") Long id){
		Evento evento = eventoService.buscarEvento(id);
		ModelAndView model = new ModelAndView("formEditarEvento");
		model.addObject("evento", evento);
		return model;
	}
	
	@PostMapping(path="/editar")
	public String atualizar(@Valid Evento evento, @RequestParam("dataInicio") String dataInicioStr, @RequestParam("dataFim") String dataFimStr, BindingResult result) throws ParseException{
		if (!verificarFormulario(evento, result)) return "formEditarEvento"; 
		
		Evento evento_salvo = eventoService.salvarEvento(evento);
		return "redirect:/evento/"+evento_salvo.getId();
	}
	
	@PostMapping(path="/editar_status")
	public String atualizarStatus(@Valid Evento evento, @RequestParam("id") Long id){
		
		Evento eventoAtivo = eventoService.buscarEvento(evento.getId());
		if (eventoAtivo != null) {
			eventoAtivo.setStatus(true);
			
			Evento atualizado = eventoService.salvarEvento(eventoAtivo);
			
			return "redirect:/evento/" + atualizado.getId();
		} else {
			return "redirect:/evento/meus_eventos";
		}
		
	}
	
	public boolean verificarFormulario(Evento evento, BindingResult result){
		Date dataAtual = new Date();
		if (evento.getNome().equals("")) return false;
		
		if (result.hasErrors()) return false;
		
		if (evento.getDataFim().before(evento.getDataInicio())) return false;
		
		if (evento.getDataInicio().before(dataAtual) ) return false;
		
		if (evento.getPorcentagemMin() < 0 || evento.getPorcentagemMin() > 100) return false;
		
		if (evento.getVagas() < 0) return false;
		
		return true;
	}

	@PostMapping(path="/convidar")
	public String convidar(@RequestParam String email, @RequestParam Long id){
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		Usuario usuario = usuarioService.getUsuario(email);
		Evento evento = eventoService.buscarEvento(id);
		if(participacaoEventoService.getPapelUsuarioEvento(usuarioLogado, evento) == Papel.ORGANIZADOR){
			if(usuario != null){
				ParticipacaoEvento pe = new ParticipacaoEvento(usuario, evento, Papel.ORGANIZADOR, true);
				participacaoEventoService.addParticipacaoEvento(pe);
			} else {
				Convite convite = new Convite(email, id);
				conviteService.addConvite(convite);
				conviteService.enviarEmail(convite);
			}
		}
		return "redirect:/evento/"+id;

	}
	
	@GetMapping(path="/excluir_organizador/{id}")
	public String excluirOrganizadorEvento(@PathVariable("id") Long id){
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		ParticipacaoEvento participacaoEvento = participacaoEventoService.getPartipacaoEvento(id);
		Usuario usuarioAntigo = usuarioService.getUsuario(participacaoEvento.getUsuario().getId());
		Evento evento = participacaoEvento.getEvento();
		
		if (participacaoEventoService.getPapelUsuarioEvento(usuarioLogado, evento) == Papel.ORGANIZADOR 
				&& usuarioLogado.getId() != usuarioAntigo.getId()) {
			
			alterarOrganizadorAtividades(evento, usuarioLogado, usuarioAntigo);
			
			participacaoEventoService.excluirParticipacaoEvento(id);
		}
		return "redirect:/evento/"+evento.getId();
	}
	
	
	@GetMapping(path="/participantes_evento/{id}")
	public ModelAndView participantesEvento(@PathVariable("id") Long id){
		
		Usuario user = usuarioService.getUsuarioLogado();
		Evento evento = eventoService.buscarEvento(id);
		List<ParticipacaoEvento> organizadores = participacaoEventoService.organizadoresEvento(id);
		List<ParticipacaoEvento> participantes = participacaoEventoService.participantesEvento(id);
		
		for (ParticipacaoEvento org : organizadores) {
			if (user.getId() == org.getUsuario().getId()) {
				if (org.getPapel() == Papel.ORGANIZADOR) {
					ModelAndView model = new ModelAndView("participantesEvento");
					model.addObject("participantes", participantes);
					model.addObject("organizadores", organizadores);
					model.addObject("usuarioLogado", user);
					model.addObject("evento", evento);
					return model;
				}
			}
		}
		return meusEventos();
	}
	
	@GetMapping(path="/excluir_participantes/{id}")
	public String excluirParticipanteEvento(@PathVariable("id") Long id) {
		
		ParticipacaoEvento participacaoEvento = participacaoEventoService.getPartipacaoEvento(id);
		Evento evento = participacaoEvento.getEvento();
		List<ParticipacaoAtividade> lista = new ArrayList<ParticipacaoAtividade>();

		for (Atividade atividade : evento.getAtividades()) {
			for (ParticipacaoAtividade participacaoAtividade : atividade.getParticipantes()) {
				if (participacaoAtividade.getUsuario().getId() == participacaoEvento.getUsuario().getId()) {
					lista.add(participacaoAtividade);
				}
			}
		}
		
		for (ParticipacaoAtividade participacaoAtividadeNova : lista) {
			participacaoAtividadeService.excluirParticipacaoAtividade(participacaoAtividadeNova.getId());
		}
		
		participacaoEventoService.excluirParticipacaoEvento(id);
		
		return "redirect:/evento/participantes_evento/"+evento.getId();
	}
	
	public void alterarOrganizadorAtividades(Evento evento, Usuario novo, Usuario antigo) {
		for (Atividade atividade : evento.getAtividades()) {
			for (ParticipacaoAtividade participacaoAtividade : atividade.getParticipantes()) {
				if (participacaoAtividade.getUsuario().getId() == antigo.getId()
						&& participacaoAtividade.getPapel() == Papel.ORGANIZADOR) {
					participacaoAtividade.setUsuario(novo);
					
					participacaoAtividadeService.salvarParticipacaoAtividade(participacaoAtividade);
				}
			}
		}
	}
}

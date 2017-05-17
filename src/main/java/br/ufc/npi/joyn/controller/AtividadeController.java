package br.ufc.npi.joyn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Atividade;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoAtividade;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.AtividadeService;
import br.ufc.npi.joyn.service.EventoService;
import br.ufc.npi.joyn.service.ParticipacaoAtividadeService;
import br.ufc.npi.joyn.service.UsuarioService;

@Controller
@RequestMapping(path = "/atividade")
public class AtividadeController {

	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ParticipacaoAtividadeService participacaoAtividadeService;
	
	@GetMapping(path="/{idEvento}/cadastrar")
	public ModelAndView cadastrarAtividade(@PathVariable("idEvento") Long idEvento){
		
		Evento evento = eventoService.buscarEvento(idEvento);
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		for (ParticipacaoEvento pe : evento.getParticipantes()) {
			if (pe.getUsuario().equals(usuarioLogado)){
				if (pe.getPapel() == Papel.ORGANIZADOR){
					ModelAndView model = new ModelAndView("formCadastroAtividade");
					Atividade atividade = new Atividade();
					atividade.setEvento(evento);
					model.addObject("atividade", atividade);
					return model;	
				}
			}
		}
		ModelAndView model = new ModelAndView("meusEventos");
		List<Evento> eventos = eventoService.getMeusEventos(usuarioLogado.getId());
		model.addObject("eventos", eventos);
		return model;
	}
	
	@PostMapping(path="/cadastrar")
	public String cadastrarAtividade(Atividade atividade){
		
		if (!verificarFormulario(atividade)) return "formCadastroAtividade";
		
		List<ParticipacaoAtividade> participantes = new ArrayList<ParticipacaoAtividade>();
		atividade.setParticipantes(participantes);
		Atividade atividadeSalva = atividadeService.salvarAtividade(atividade);
		
		Evento evento = eventoService.buscarEvento(atividadeSalva.getEvento().getId());
		List<Atividade> atividades =  evento.getAtividades();
		atividades.add(atividadeSalva);
		evento.setAtividades(atividades);
		eventoService.salvarEvento(evento);
		
		Usuario logado = usuarioService.getUsuarioLogado();
		ParticipacaoAtividade participacaoAtividadeSalva = participacaoAtividadeService.adicionarAtividade(logado, atividadeSalva);		
		
		participantes = atividadeSalva.getParticipantes();
		participantes.add(participacaoAtividadeSalva);
		atividadeService.salvarAtividade(atividadeSalva);
		
		return "redirect:/atividade/" + atividadeSalva.getId();
	}
	
	@GetMapping(path="/{id}")
	public ModelAndView detalhesAtividade(@PathVariable("id") Long id){
		ModelAndView model = new ModelAndView("detalhesAtividade");
		Atividade atividade =  atividadeService.buscarAtividade(id);
		model.addObject("atividade", atividade);
		return model;
	}
	
	public boolean verificarFormulario(Atividade atividade){
		if (atividade.getNome() == null || atividade.getDescricao() == null || 
				atividade.getDias() == null || atividade.getTipo() == null) return false;
		
		if (atividade.getDias() < 0) return false;
				
		if (atividade.getMinimoParaFreq() != null){
			if (atividade.getMinimoParaFreq() < 0 || 
					atividade.getMinimoParaFreq() > atividade.getDias()) return false;			
		}
		
		if (atividade.getPontuacao() != null){
			if (atividade.getPontuacao() < 0 ) return false;
		}
		
		return true;
	}
}

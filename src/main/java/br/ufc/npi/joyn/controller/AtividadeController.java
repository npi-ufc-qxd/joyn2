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
import br.ufc.npi.joyn.service.ParticipacaoEventoService;
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
	
	@Autowired
	ParticipacaoEventoService participacaoEventoService;
	
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
		participacaoAtividadeService.criarParticipacaoAtividade(logado, atividadeSalva);		
		
		return "redirect:/atividade/" + atividadeSalva.getId();
	}
	
	@GetMapping(path="/{id}")
	public ModelAndView detalhesAtividade(@PathVariable("id") Long id){
		ModelAndView model = new ModelAndView("detalhesAtividade");
		Atividade atividade =  atividadeService.buscarAtividade(id);
		model.addObject("atividade", atividade);
		return model;
	}
	
	@GetMapping(path="/editar/{id}")
	public ModelAndView editarAtividade(@PathVariable("id") Long id){
		ModelAndView model = new ModelAndView("formEditarAtividade");
		Atividade atividade = atividadeService.buscarAtividade(id);
		model.addObject("atividade", atividade);
		return model;
	}
	
	@PostMapping(path="/editar")
	public String atualizarAtividade(Atividade atividade){
		
		if (!verificarFormulario(atividade)) return "formEditarAtividade"; 
		
		Atividade atividadeSalva = atividadeService.salvarAtividade(atividade);
		return "redirect:/atividade/"+atividadeSalva.getId();
	}
	
	@GetMapping(path="/excluir/{id}")
	public String excluirAtividade(@PathVariable("id") Long id){
		Atividade atividade = atividadeService.buscarAtividade(id);
				
		for (ParticipacaoAtividade pa: atividade.getParticipantes()) {
			pa.getUsuario().getParticipacaoAtividade().remove(pa);
			usuarioService.atualizaUsuario(pa.getUsuario());
		}

		Evento evento = eventoService.buscarEvento(atividade.getEvento().getId());
		evento.getAtividades().remove(atividade);
		eventoService.salvarEvento(evento);
		
		atividadeService.removerAtividade(atividade);		
		return "redirect:/evento/"+evento.getId();
	}	

	@GetMapping(path="/excluir_participante/{id}")
	public String excluirParticipante(@PathVariable("id") Long idParticipacaoEvento){
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		ParticipacaoAtividade paExcluir = participacaoAtividadeService.getParticipacaoAtividade(idParticipacaoEvento);
		Atividade atividade = paExcluir.getAtividade();
		Evento evento = paExcluir.getAtividade().getEvento();
		
		if(participacaoEventoService.getPapelUsuarioEvento(usuarioLogado, evento) == Papel.ORGANIZADOR)
			participacaoAtividadeService.excluirParticipacaoAtividade(idParticipacaoEvento);
		return "redirect:/atividade/"+atividade.getId()+"/ver_participantes";
	}

	@GetMapping(path="/{id}/ver_participantes")
	public ModelAndView verParticipantes(@PathVariable("id") Long idAtividade){
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		Atividade atividade = atividadeService.buscarAtividade(idAtividade);
		
		if(participacaoEventoService.getPapelUsuarioEvento(usuarioLogado, atividade.getEvento()) == Papel.ORGANIZADOR){
			ModelAndView model = new ModelAndView("listarParticipantesAtividade");
			model.addObject("atividade", atividade);
			return model;
		}
		
		return new ModelAndView("redirect:/evento/meus_eventos");

	}
	

	@GetMapping(path="/addparticipantes/{idUser}/{atv}")
	public String addParticipantes(@PathVariable("idUser") Long usuarioid, @PathVariable("atv") Long atividadeid){
		Usuario usuario = usuarioService.getUsuario(usuarioid);
		Atividade atividade = atividadeService.buscarAtividade(atividadeid);
		participacaoAtividadeService.adicionarAtividade(usuario, atividade);
		return "redirect:/atividade/"+atividade.getId()+"/ver_participantes";
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

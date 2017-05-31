package br.ufc.npi.joyn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.ParticipacaoEventoRepository;

@Service
public class ParticipacaoEventoService {

	@Autowired
	ParticipacaoEventoRepository participacaoEventoRepository;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	public ParticipacaoEvento addParticipacaoEvento(ParticipacaoEvento participacaoEvento){
		ParticipacaoEvento peSalvo = participacaoEventoRepository.save(participacaoEvento);
		participacaoEvento.getUsuario().getParticipacaoEvento().add(peSalvo);
		participacaoEvento.getEvento().getParticipantes().add(peSalvo);
		eventoService.salvarEvento(participacaoEvento.getEvento());
		usuarioService.atualizaUsuario(participacaoEvento.getUsuario());
		return peSalvo;
	}
	
	public void excluirParticipacaoEvento(Long id){
		ParticipacaoEvento peSalvo = participacaoEventoRepository.findOne(id);
		peSalvo.getUsuario().getParticipacaoEvento().remove(peSalvo);
		peSalvo.getEvento().getParticipantes().remove(peSalvo);
		eventoService.salvarEvento(peSalvo.getEvento());
		usuarioService.atualizaUsuario(peSalvo.getUsuario());
		participacaoEventoRepository.delete(id);
	}
	
	public ParticipacaoEvento getPartipacaoEvento(Long id){
		return participacaoEventoRepository.findOne(id);
	}
	
	public Papel getPapelUsuarioEvento(Usuario usuario, Evento evento){
		return participacaoEventoRepository.getPapelUsuarioEvento(usuario.getId(), evento.getId());
	}
	
	public List<ParticipacaoEvento> participantesEvento(Long id){
		return participacaoEventoRepository.getListaParticipantes(id);
	}
	
	public List<ParticipacaoEvento> organizadoresEvento(Long id){
		return participacaoEventoRepository.getListaOrganizadores(id);
	}
	
	public boolean verificarParticipacaoEvento(Usuario usuario, Evento evento){
		for (ParticipacaoEvento participacaoEvento : evento.getParticipantes()) {
			if(usuario.getId() == participacaoEvento.getUsuario().getId()){
				return true;
			}
		}
		return false;
	}
}

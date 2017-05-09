package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.ParticipacaoEvento;
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
}

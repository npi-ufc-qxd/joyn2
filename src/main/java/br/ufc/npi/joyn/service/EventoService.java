package br.ufc.npi.joyn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepo;
	
	public Evento salvarEvento(Evento evento){
		
		Evento eventoSalvo = eventoRepo.save(evento);
		
		return eventoSalvo;
	}
	
	public Evento buscarEvento(Long id){
		Evento evento = eventoRepo.findOne(id);
		return evento;
	}
	
	public List<Evento> getMeusEventos(Long id){
		return eventoRepo.meusEventos(id);
	}
	
	public List<Evento> getMeusEventosPorNome(Long usuarioId, String nome){
		return eventoRepo.meusEventosPorNome(usuarioId, nome);
	}
}

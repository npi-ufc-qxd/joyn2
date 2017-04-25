package br.ufc.npi.joyn.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepo;
	
	public Evento cadastrarEvento(String nome, String descricao, Date dataInicial, Date dataFinal,
			Integer quantVagas, String local, boolean gameficado){
		
		Evento evento = new Evento();
		evento.setNome(nome);
		evento.setDescricao(descricao);
		evento.setDataInicio(dataInicial);
		evento.setDataFim(dataFinal);
		evento.setVagas(quantVagas);
		evento.setLocal(local);
		evento.setGameficado(gameficado);
		
		eventoRepo.save(evento);
		
		return evento;
	}
	
	public Evento buscarEvento(Long id){
		Evento evento = eventoRepo.findOne(id);
		return evento;
	}
}

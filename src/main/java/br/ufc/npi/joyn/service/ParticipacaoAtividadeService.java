package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Atividade;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoAtividade;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.ParticipacaoAtividadeRepository;

@Service
public class ParticipacaoAtividadeService {
	
	@Autowired
	ParticipacaoAtividadeRepository participacaoRepo;
	
	public ParticipacaoAtividade adicionarAtividade(Usuario usuario, Atividade atividade){
		ParticipacaoAtividade participacaoAtividade = new ParticipacaoAtividade();
		participacaoAtividade.setUsuario(usuario);
		participacaoAtividade.setAtividade(atividade);
		participacaoAtividade.setPapel(Papel.ORGANIZADOR);
		
		if (atividade.getParticipantes().size() < atividade.getVagas()){
			participacaoAtividade.setStatus(true);
		}else{
			participacaoAtividade.setStatus(false);
		}

		return participacaoRepo.save(participacaoAtividade); 
	}
	
	public void removerAtividade(ParticipacaoAtividade pa){
		participacaoRepo.delete(pa);
	}

}

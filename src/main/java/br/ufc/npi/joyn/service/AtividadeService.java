package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Atividade;
import br.ufc.npi.joyn.repository.AtividadeRepository;

@Service
public class AtividadeService {
	
	@Autowired
	AtividadeRepository atividadeRepo;
	
	public Atividade buscarAtividade(Long id){
		return atividadeRepo.getAtividade(id);
	}

	public Atividade salvarAtividade(Atividade atividade){
		Atividade atividadeSalva = atividadeRepo.save(atividade);
		return atividadeSalva;
	}
	
	public void removerAtividade(Atividade atividade){
		atividadeRepo.delete(atividade);
	}
}

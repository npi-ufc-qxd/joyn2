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
	ParticipacaoAtividadeRepository participacaoAtividadeRepository;
	
	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	UsuarioService usuarioService;
	
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

		ParticipacaoAtividade paSalvo = participacaoRepo.save(participacaoAtividade);
		usuario.getParticipacaoAtividade().add(paSalvo);
		atividade.getParticipantes().add(paSalvo);
		usuarioService.atualizaUsuario(usuario);
		atividadeService.salvar(atividade);
		return paSalvo; 
	}
	
	public void excluirParticipacaoAtividade(Long id){
		ParticipacaoAtividade paSalvo = getParticipacaoAtividade(id);
		paSalvo.getUsuario().getParticipacaoAtividade().remove(paSalvo);
		paSalvo.getAtividade().getParticipantes().remove(paSalvo);
		usuarioService.atualizaUsuario(paSalvo.getUsuario());
		atividadeService.salvar(paSalvo.getAtividade());
		participacaoAtividadeRepository.delete(id);
	}
	
	public ParticipacaoAtividade getParticipacaoAtividade(Long id){
		return participacaoAtividadeRepository.findOne(id);
	}

}

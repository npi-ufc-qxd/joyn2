package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Atividade;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoAtividade;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.ParticipacaoAtividadeRepository;

@Service
public class ParticipacaoAtividadeService {
	
	
	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ParticipacaoAtividadeRepository participacaoRepo;
	
	@Autowired
	ParticipacaoEventoService participacaoEvento;
	
	public ParticipacaoAtividade adicionarAtividade(Usuario usuario, Atividade atividade){
		ParticipacaoAtividade participacaoAtividade = new ParticipacaoAtividade();
		
		participacaoAtividade.setUsuario(usuario);
		participacaoAtividade.setAtividade(atividade);
		participacaoAtividade.setPapel(Papel.PARTICIPANTE);
		
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
		if(participacaoEvento.verificarParticipacaoEvento(usuario, atividade.getEvento()) == false){
			participacaoEvento.addParticipacaoEvento(new ParticipacaoEvento(usuario, atividade.getEvento(), Papel.PARTICIPANTE, true));
		}
		return paSalvo; 
	}
	
	public void excluirParticipacaoAtividade(Long id){
		ParticipacaoAtividade paSalvo = getParticipacaoAtividade(id);
		paSalvo.getUsuario().getParticipacaoAtividade().remove(paSalvo);
		paSalvo.getAtividade().getParticipantes().remove(paSalvo);
		usuarioService.atualizaUsuario(paSalvo.getUsuario());
		atividadeService.salvar(paSalvo.getAtividade());
		participacaoRepo.delete(id);
	}
	
	public ParticipacaoAtividade getParticipacaoAtividade(Long id){
		return participacaoRepo.findOne(id);
	}
	
	public void removerAtividade(ParticipacaoAtividade pa){
		participacaoRepo.delete(pa);
	}

}

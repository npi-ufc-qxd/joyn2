package br.ufc.npi.joyn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Convite;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.repository.ConviteRepository;
import br.ufc.npi.joyn.util.Constants;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Service
@ComponentScan("br.ufc.quixada.npi.service")
public class ConviteService {

	@Autowired
	ConviteRepository conviteRepository;
	
	@Autowired
	SendEmailService emailService;
	
	@Autowired
	EventoService eventoService;
	
	public void addConvite(Convite convite){
		conviteRepository.save(convite);
	}
	
	public Convite getConvite(Long id){
		return conviteRepository.findOne(id);
	}
	
	public Convite getConvite(String email){
		return conviteRepository.findByEmail(email);
	}
	
	public List<Convite> getConvites(Long idEvento){
		return conviteRepository.findByIdEvento(idEvento);
	}
	
	public void enviarEmail(Convite convite){
		Evento evento = eventoService.buscarEvento(convite.getIdEvento());
		EmailBuilder emailBuilder = new EmailBuilder(
				Constants.EMAIL_REMETENTE_NOME,
				Constants.EMAIL_REMETENTE_EMAIL,
				Constants.EMAIL_ASSUNTO_CONVITE, 
				convite.getEmail(), 
				"Você foi convidado para organizar o evento: " + evento.getNome() 
				+ "\n Faca seu cadastro em: " + Constants.BASE_URL + "/usuario/cadastrar");
				Email emailConvite = new Email(emailBuilder);
				emailService.sendEmail(emailConvite);
	}
	
	public void excluirConvite(Convite convite){
		conviteRepository.delete(convite);
	}
}

package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Token;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.TokenRepository;
import br.ufc.npi.joyn.util.Constants;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Service
@ComponentScan("br.ufc.quixada.npi.service")
public class TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	SendEmailService emailService;

	public Token buscarPorUsuario(Usuario usuario) {
		return tokenRepository.findByUsuario(usuario);
	}

	public Token buscar(String token) {
		return tokenRepository.findOne(token);
	}

	public boolean existe(String token) {
		return tokenRepository.exists(token);
	}

	public void salvar(Token token) {
		tokenRepository.save(token);
	}

	public void deletar(Token token) {
		tokenRepository.delete(token);
	}
	
	public void enviarEmailRecuperacao(Token token){
		EmailBuilder emailBuilder = new EmailBuilder(
				Constants.EMAIL_REMETENTE_NOME,
				Constants.EMAIL_REMETENTE_EMAIL,
				Constants.EMAIL_ASSUNTO_REC_SENHA, 
				token.getUsuario().getEmail(),
				"\n Altere sua senha em: " + Constants.BASE_URL + "/usuario/alterarsenha/" + token.getToken());
		Email emailSenha = new Email(emailBuilder);
		emailService.sendEmail(emailSenha);
	}
}

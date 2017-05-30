package br.ufc.npi.joyn.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Token;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.UsuarioRepository;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Service
@ComponentScan("br.ufc.quixada.npi.service")
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	SendEmailService service;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UsuarioService() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}
	
	public Usuario salvarUsuario(Usuario usuario){
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizaUsuario(Usuario usuario){
		return usuarioRepository.save(usuario);
	}
	
	public boolean logar(String email, String senha){
		Usuario userBanco = usuarioRepository.findByEmail(email);
		if(userBanco != null && new BCryptPasswordEncoder().matches(senha, userBanco.getSenha())) return true;
		else return false;
	}
	
	public boolean logar(Usuario usuario){
		Usuario userBanco = usuarioRepository.findByEmail(usuario.getEmail());
		if(userBanco != null && new BCryptPasswordEncoder().matches(usuario.getSenha(), userBanco.getSenha())) return true;
		else return false;
	}
	
	public boolean compararSenha(String senhaCrip, String senhaLimpa){
		if(new BCryptPasswordEncoder().matches(senhaLimpa, senhaCrip)) return true;
		else return false;
	}
	
	public List<Usuario> getTodosUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuario(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario getUsuario(Long id){
		return usuarioRepository.findOne(id);
	}
	
	public Usuario getUsuarioLogado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Usuario usuarioLogado = this.getUsuario(email);
		return usuarioLogado;
	}
	
	public void recuperarSenha(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if (usuario != null) {
			Token token = null;
			token = tokenService.buscarPorUsuario(usuario);
			
			if (token == null) {
				token = new Token();
				token.setUsuario(usuario);
				token.setToken(UUID.randomUUID().toString());
				tokenService.salvar(token);
			}

			EmailBuilder emailBuilder = new EmailBuilder("Joyn",
					 "joyn@npi.com.br",
					 "Recuperacao de senha", 
					 email,
					 "\n Altera sua senha em: http://localhost:8080/usuario/alterarsenha/" + token.getToken());
			Email emailSenha = new Email(emailBuilder);
			service.sendEmail(emailSenha);
		}
	}
	
	public void novaSenha(String tokenStr, String senha) {
		Token token = tokenService.buscar(tokenStr);
		if (token != null && !token.expirou()) {
			Usuario usuario = token.getUsuario();
			usuario.setSenha(senha);
			salvarUsuario(usuario);
			tokenService.deletar(token);
		}
		
		if(token != null && token.expirou()) tokenService.deletar(token);
	}

}

package br.ufc.npi.joyn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.UsuarioService;

@Component
public class AuthenticationProviderJoyn implements AuthenticationProvider {

	@Autowired
	UsuarioService usuarioService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String email = auth.getName();
		String senha = auth.getCredentials().toString();
		
		if(usuarioService.logar(email, senha)){
			Usuario userBanco = usuarioService.getUsuario(email);
			return new UsernamePasswordAuthenticationToken(email, senha, userBanco.getAuthorities());
		}
		
		throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}

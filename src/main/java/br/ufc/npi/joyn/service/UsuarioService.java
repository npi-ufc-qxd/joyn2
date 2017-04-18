package br.ufc.npi.joyn.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UsuarioService() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}
	
	public void salvarUsuario(Usuario usuario){
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
	}
	
	public boolean logar(Usuario usuario){
		Usuario userBanco = usuarioRepository.findByEmail(usuario.getEmail());
		if(new BCryptPasswordEncoder().matches(usuario.getSenha(), userBanco.getSenha())) return true;
		else return false;
	}
	
	public List<Usuario> getTodosUsuarios(){
		return usuarioRepository.findAll();
	}

}

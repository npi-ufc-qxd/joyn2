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
	UsuarioRepository usuarioRepository;
		
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public void salvarUsuario(Usuario usuario){
		
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		
		usuarioRepository.save(usuario);
	}
	
	public List<Usuario> getTodosUsuarios(){
		return usuarioRepository.findAll();
	}

}

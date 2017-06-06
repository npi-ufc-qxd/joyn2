package br.ufc.npi.joyn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.Token;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.EventoService;
import br.ufc.npi.joyn.service.UsuarioService;

@RestController
@RequestMapping(path="/usuariorest")
public class UsuarioRestController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	EventoService eventoService;

    @RequestMapping("/token")
    public Token greeting() {
        return new Token();
    }
    
    @GetMapping(path="/meus_eventos")
	public List<Evento> meusEventos(){
		Usuario logado = usuarioService.getUsuarioLogado();
		List<Evento> eventos = eventoService.getMeusEventos(logado.getId());		
		return eventos;
	}
    
    @GetMapping(path="/usuario")
	public Usuario usuario(){
		return usuarioService.getUsuarioLogado();
	}
    
    @RequestMapping(value="/csrf-token", method=RequestMethod.GET)
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return token.getToken();
    }
    
    
    @PostMapping(path="/cadastrar", consumes={"application/json;charset=UTF-8"})
	public @ResponseBody Usuario cadastrar(@RequestBody Usuario usuario){
    	usuario.setPapel(Papel.USUARIO);
		Usuario userBanco = usuarioService.salvarUsuario(usuario);
		return userBanco;
	}
    
    @PostMapping(path="/teste")
   	public @ResponseBody String teste(@RequestBody Usuario teste){
   		return new String("testeee");
   	}
    
    
}

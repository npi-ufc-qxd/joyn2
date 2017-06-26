package br.ufc.npi.joyn.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@RestController
@RequestMapping(path="/usuariorest")
public class UsuarioRestController {
	
	@Autowired
	UsuarioService usuarioService;
	
    @RequestMapping(value="/csrf-token", method=RequestMethod.GET)
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return token.getToken();
    }
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario login) throws ServletException {

        String jwtToken = "";

        if (login.getEmail() == null || login.getPassword() == null) {
            throw new ServletException("Entre com usuario e senha");
        }

        String email = login.getEmail();
        String password = login.getPassword();

        Usuario user = usuarioService.getUsuario(email);

        if (user == null)
            throw new ServletException("Email nao encontrado");
        
        if (!usuarioService.compararSenha(user.getSenha(), password))
            throw new ServletException("Login invalido");

        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        return jwtToken;
    }
    
    
}

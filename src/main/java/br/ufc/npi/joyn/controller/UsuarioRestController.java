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
    
    /*@PostMapping(path="/cadastrar", consumes={"application/json;charset=UTF-8"})
	public @ResponseBody Usuario cadastrar(@RequestBody Usuario usuario){
    	usuario.setPapel(Papel.USUARIO);
		Usuario userBanco = usuarioService.salvarUsuario(usuario);
		return userBanco;
	}*/
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario login) throws ServletException {

        String jwtToken = "";

        if (login.getEmail() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String email = login.getEmail();
        String password = login.getPassword();

        Usuario user = usuarioService.getUsuario(email);

        if (user == null) {
            throw new ServletException("User email not found.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        return jwtToken;
    }
    
    
}

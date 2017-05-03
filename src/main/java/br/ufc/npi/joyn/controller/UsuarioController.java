package br.ufc.npi.joyn.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.UsuarioService;
import br.ufc.npi.joyn.util.JoynFileUtil;

@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping(path = "/novo")
	public ModelAndView cadastroUsuario() {
		ModelAndView model = new ModelAndView("formCadastroUsuario");
		model.addObject(new Usuario());
		return model;
	}

	@PostMapping(path = "/novo")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, @RequestParam(value="image", required=false) MultipartFile imagem) throws IOException {
		if (result.hasErrors()) return "formCadastroUsuario";
		String nomeImagem = String.valueOf(usuario.getNome());
		JoynFileUtil.savarImagem(imagem, nomeImagem);
		usuario.setFotoUrl(JoynFileUtil.urlArquivo(nomeImagem));
		usuarioService.salvarUsuario(usuario);
		return "redirect:/usuario/novo";
	}
	
	@GetMapping(path = "/logar")
	public ModelAndView loginUsuario() {
		ModelAndView model = new ModelAndView("formLoginUsuario");
		model.addObject(new Usuario());
		return model;
	}
	
	@PostMapping(path = "/logar")
	public ModelAndView fazerLogin(Usuario usuario) throws MalformedURLException {
		ModelAndView model = new ModelAndView("homeUsuario");
		ModelAndView model2 = new ModelAndView("formLoginUsuario");
		Usuario userBanco = usuarioService.getUsuario(usuario.getEmail());
		model.addObject("usuario", userBanco);
		model2.addObject(new Usuario());
		if(usuarioService.logar(usuario)) return model;
		else return model2;
	}
	
	@GetMapping(path="/starter")
	public String starter(){
		return "starter";
	}

}

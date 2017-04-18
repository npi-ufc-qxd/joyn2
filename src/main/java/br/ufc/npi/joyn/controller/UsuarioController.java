package br.ufc.npi.joyn.controller;

import java.io.IOException;
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
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, @RequestParam(value="image", required=false) MultipartFile image) {
		if (result.hasErrors()) return "formCadastroUsuario";
		usuarioService.salvarUsuario(usuario);
		try {
			JoynFileUtil.salvarImagemUsuario(image, usuario);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/usuario/novo";
	}
	
	@GetMapping(path = "/logar")
	public ModelAndView loginUsuario() {
		ModelAndView model = new ModelAndView("formLoginUsuario");
		model.addObject(new Usuario());
		return model;
	}
	
	@PostMapping(path = "/logar")
	public String fazerLogin(Usuario usuario) {
		if(usuarioService.logar(usuario)) return "homeUsuario";
		else return "redirect:/usuario/logar";
	}

}

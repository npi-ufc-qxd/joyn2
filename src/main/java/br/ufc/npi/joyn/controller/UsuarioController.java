package br.ufc.npi.joyn.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.UsuarioService;

@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(path = "/novo", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario) {
		return "cadastroUsuario";
	}

	@RequestMapping(path = "/novo", method = RequestMethod.POST)
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors()) {
			return "cadastroUsuario";
		}

		usuarioService.salvarUsuario(usuario);

		return "redirect:/usuario/novo";
	}

}

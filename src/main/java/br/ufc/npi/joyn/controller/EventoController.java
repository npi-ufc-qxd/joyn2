package br.ufc.npi.joyn.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufc.npi.joyn.service.EventoService;

@Controller
@RequestMapping(path="/evento")
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@PostMapping(path="/cadastrar")
	public String cadastrarEvento(@RequestParam String nome, @RequestParam String descricao, @RequestParam Date dataInicial,
			@RequestParam Date dataFinal, @RequestParam Integer quantVagas, @RequestParam String local, @RequestParam boolean gamificacao){
		
		eventoService.cadastrarEvento(nome, descricao, dataInicial, dataFinal, quantVagas, local);
		return "redirect:/formLoginUsuario";
	}
}

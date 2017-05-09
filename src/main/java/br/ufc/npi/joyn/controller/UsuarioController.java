package br.ufc.npi.joyn.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.joyn.model.Convite;
import br.ufc.npi.joyn.model.Evento;
import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoEvento;
import br.ufc.npi.joyn.model.Usuario;
import br.ufc.npi.joyn.service.ConviteService;
import br.ufc.npi.joyn.service.EventoService;
import br.ufc.npi.joyn.service.ParticipacaoEventoService;
import br.ufc.npi.joyn.service.UploadArquivoService;
import br.ufc.npi.joyn.service.UsuarioService;

@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UploadArquivoService uploadArquivoService;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	ConviteService conviteService;
	
	@Autowired
	ParticipacaoEventoService participacaoEventoService;
	
	private final String pastaImagensUsuarios = "imagens" + File.separator +"usuarios";
	
	@GetMapping(path = "/novo")
	public ModelAndView cadastroUsuario(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("formCadastroUsuario");
		model.addObject(new Usuario());
		return model;
	}
	
	@PostMapping(path = "/novo")
	public String salvarUsuario(HttpServletRequest request, @Valid Usuario usuario, BindingResult result, @RequestParam(value="imagem", required=false) MultipartFile imagem) throws IOException {
		if (result.hasErrors()) return "formCadastroUsuario";
		usuario.setPapel(Papel.USUARIO);
		
		Usuario userBanco = usuarioService.salvarUsuario(usuario);
				
		Convite convite = conviteService.getConvite(usuario.getEmail());
		if(convite != null){
			Evento eventoConvidado = eventoService.buscarEvento(convite.getIdEvento());
			ParticipacaoEvento pe = new ParticipacaoEvento(userBanco, eventoConvidado, Papel.ORGANIZADOR, true);
			participacaoEventoService.addParticipacaoEvento(pe);
		}
		
		if(imagem != null){
			salvarImagemUsuario(imagem, usuario.getId());
			String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
			String fotoUrl = baseUrl + "/usuario/imagens/" + usuario.getId();
			usuario.setFotoUrl(fotoUrl);
			usuarioService.atualizaUsuario(usuario);
		}
		
		return "redirect:/usuario/novo";
	}
	
	public String salvarImagemUsuario(MultipartFile imagem, Long idUsuario) throws IOException{
		return uploadArquivoService.store(imagem, pastaImagensUsuarios, String.valueOf(idUsuario));
	}
	
	@GetMapping(path = "/logar")
	public ModelAndView loginUsuario() {
		ModelAndView model = new ModelAndView("formLoginUsuario");
		model.addObject(new Usuario());
		return model;
	}
	
	
	@GetMapping(path = "/home")
	public ModelAndView homeUsuario() {
		ModelAndView model = new ModelAndView("homeUsuario");
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		model.addObject("usuario", usuarioLogado);
		return model;
	}
	
	@GetMapping("/imagens/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) throws IOException {

        Resource file = uploadArquivoService.loadAsResource(pastaImagensUsuarios + File.separator + id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

}

package br.ufc.npi.joyn.controller;

import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
import br.ufc.npi.joyn.util.Constants;

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
	
	@GetMapping(path = "/cadastrar")
	public ModelAndView cadastroUsuario(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("formCadastroUsuario");
		model.addObject(new Usuario());
		return model;
	}
	
	@PostMapping(path = "/cadastrar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, @RequestParam(value="imagem", required=false) MultipartFile imagem) throws IOException {
		if (result.hasErrors()) return "formCadastroUsuario";
		usuario.setPapel(Papel.USUARIO);
		Usuario userBanco = usuarioService.salvarUsuario(usuario);
		
		Convite convite = conviteService.getConvite(usuario.getEmail());
		if(convite != null){
			Evento eventoConvidado = eventoService.buscarEvento(convite.getIdEvento());
			ParticipacaoEvento pe = new ParticipacaoEvento(userBanco, eventoConvidado, Papel.ORGANIZADOR, true);
			participacaoEventoService.addParticipacaoEvento(pe);
		}
		
		String fotoUrl = Constants.BASE_URL + "/usuario/imagens/" + userBanco.getId();
		userBanco.setFotoUrl(fotoUrl);
		usuarioService.atualizaUsuario(userBanco);
		
		if(imagem != null && !imagem.isEmpty())
			salvarImagemUsuario(imagem, userBanco.getId());

		return "redirect:/usuario/logar";

	}
	
	public void salvarImagemUsuario(MultipartFile imagem, Long idUsuario) throws IOException{
		String mimetype= new MimetypesFileTypeMap().getContentType(imagem.getOriginalFilename());
        String type = mimetype.split("/")[0];
        if(type.equals("image"))
        	uploadArquivoService.store(imagem, Constants.PATH_IMAGENS_USUARIOS, String.valueOf(idUsuario));
		else throw new IOException("Arquivo não é uma imagem");
	}
	
	@GetMapping(path = "/editar")
	public ModelAndView editarUsuarioForm() {
		ModelAndView model = new ModelAndView("editarUsuario");
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		model.addObject("usuario", usuarioLogado);
		return model;
	}
	
	@PostMapping(path = "/editar")
	public String editarUsuario(Usuario usuario, @RequestParam String senhaAtual, @RequestParam(value="imagem", required=false) MultipartFile imagem) throws IOException {
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		Usuario usuarioBanco = usuarioService.getUsuario(usuarioLogado.getEmail());
		
		if(imagem != null && !imagem.isEmpty())
			salvarImagemUsuario(imagem, usuarioBanco.getId());
		
		if(!usuario.getNome().isEmpty())
			usuarioBanco.setNome(usuario.getNome());
		if(!usuario.getEmail().isEmpty())
			usuarioBanco.setEmail(usuario.getEmail());
		usuarioBanco = usuarioService.atualizaUsuario(usuarioBanco);
		
		if(senhaAtual != null && !senhaAtual.isEmpty()){
			if(usuarioService.compararSenha(usuarioBanco.getSenha(), senhaAtual)){
				usuarioBanco.setSenha(usuario.getSenha());
				usuarioService.salvarUsuario(usuarioBanco);
			}
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioBanco.getEmail(), usuarioBanco.getSenha(), usuarioBanco.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/usuario/editar";
	}	
	
	
	@GetMapping(path="/starter")
	public String starter(){
		return "starter";
	}
	
	@GetMapping(path="/recuperarsenha")
	public String recuperarsenha(){
		return "formRecuperarSenha";
	}
	
	@PostMapping(path = "/recuperarsenha")
	public String recuperarsenha(@RequestParam String email){
		usuarioService.recuperarSenha(email);
		return "redirect:/usuario/logar";
	}
	
	@GetMapping(path="/alterarsenha/{token}")
	public ModelAndView alterarSenha(@PathVariable String token){
		ModelAndView model = new ModelAndView("formAlterarSenha");
		model.addObject("token", token);
		return model;
	}
	
	@PostMapping(path = "/novasenha")
	public String novasenha(@RequestParam String token, @RequestParam String novaSenha){
		usuarioService.novaSenha(token, novaSenha);
		return "redirect:/usuario/logar";
	}
	

	@GetMapping("/imagens/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {

        Resource file;
		try {
			file = uploadArquivoService.loadAsResource(Constants.PATH_IMAGENS_USUARIOS + File.separator + id);
		} catch (IOException e) {
			return ResponseEntity.noContent().build();
		}
		
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

}

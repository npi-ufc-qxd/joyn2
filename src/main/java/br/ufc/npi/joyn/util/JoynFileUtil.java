package br.ufc.npi.joyn.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.npi.joyn.model.Usuario;

public class JoynFileUtil {
	
	private static final Path rootLocation = Paths.get("upload-dir");
 
    public static void salvarImagemUsuario(MultipartFile imagem, Usuario usuario) throws IOException{
    	Files.copy(imagem.getInputStream(), rootLocation.resolve(String.valueOf(usuario.getId())));
    }
 
    public static Resource loadFile(String filename) throws MalformedURLException {
        Path file = rootLocation.resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if(resource.exists() || resource.isReadable()) {
            return resource;
        }else{
            throw new RuntimeException("FAIL!");
        }
    }

}

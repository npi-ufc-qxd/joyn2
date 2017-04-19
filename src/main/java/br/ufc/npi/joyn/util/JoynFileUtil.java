package br.ufc.npi.joyn.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class JoynFileUtil {
	
	private static final Path rootLocation = Paths.get("upload-dir");

	private static boolean imagemValida(MultipartFile imagem){
		try {
			ImageIO.read(imagem.getInputStream()).toString();
			return true;
		} catch (IOException e) { return false; }
	}
	
	public static void savarImagem(MultipartFile imagem, String nome) throws IOException{
		if(imagemValida(imagem)) Files.copy(imagem.getInputStream(), rootLocation.resolve(nome));
	}
	
	public static String urlArquivo(String nome){
		return rootLocation.resolve(nome).toString();
	}

}

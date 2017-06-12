package br.ufc.npi.joyn.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.npi.joyn.util.Constants;

@Service
public class UploadArquivoService {

	private Path rootLocation;
	
	public UploadArquivoService() {
		rootLocation = Paths.get(Constants.PATH_UPLOAD);
	}
	
	public String store(MultipartFile arquivoUpload, String pastaDeDestino, String nomeArquivo) throws IOException {	
		
	 	String caminho = new StringBuilder(rootLocation.toString())
	 			.append(File.separator)
	 			.append(pastaDeDestino)
	 			.append(File.separator)			 			
	 			.toString();
	 	
        if (arquivoUpload.isEmpty())
            throw new IOException("Arquivo vazio");
        
        File pasta = new File(caminho);
        if(!pasta.exists() && !pasta.mkdirs())
    		throw new RuntimeException("Destino inexistente");
    	
        String caminhoDoArquivo = new StringBuilder(caminho)
        		.append(nomeArquivo)
        		.toString();
        
		Files.copy(arquivoUpload.getInputStream(), Paths.get(caminhoDoArquivo), StandardCopyOption.REPLACE_EXISTING);
		return caminhoDoArquivo;
	}

	public Stream<Path> loadAll() throws IOException {
	        return Files.walk(this.rootLocation, 1)
	                .filter(path -> !path.equals(this.rootLocation))
	                .map(path -> this.rootLocation.relativize(path));
	}
	
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	public Resource loadAsResource(String filename) throws IOException {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if(resource.exists() || resource.isReadable()) 
            return resource;
        else 
        	throw new IOException("Falha ao ler arquivo");
	}
	
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

}

package br.ufc.npi.joyn.util;

import java.io.File;

public class Constants {
	
	public static final String BASE_URL = "http://homologacaosistemas.quixada.ufc.br/joyn";

	public static final String EMAIL_REMETENTE_NOME = "Joyn"; 
	public static final String EMAIL_REMETENTE_EMAIL = "joyn@npi.com.br";
	public static final String EMAIL_ASSUNTO_CONVITE = "Convite para organizar evento";
	public static final String EMAIL_ASSUNTO_REC_SENHA = "Recuperacao de senha";
	
	public static final String PATH_UPLOAD = "upload-dir";
	public static final String PATH_IMAGENS_USUARIOS = "imagens" + File.separator +"usuarios";
	public static final int TAM_MAX_IMG_64 = 7000000;
	
	public static final long TOKEN_EXPIRAR_MINUTOS = 24 * 60;
}
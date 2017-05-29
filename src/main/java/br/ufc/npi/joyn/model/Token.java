package br.ufc.npi.joyn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Token {

	public static long TEMPO_EXPIRAR_MINUTOS = 24 * 60;
	public static long TEMPO_EXPIRAR = 60000 * TEMPO_EXPIRAR_MINUTOS;
	
	@Id
	private String token;

	@OneToOne
	private Usuario usuario;
	
	private Long tempoCriacao;

	public Token() {
		tempoCriacao = new Date().getTime();
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getTempoCriacao() {
		return tempoCriacao;
	}

	public void setTempoCriacao(Long tempoCriacao) {
		this.tempoCriacao = tempoCriacao;
	}
	
	public boolean expirou(){
		Long tempoPassadoMS = new Date().getTime() - getTempoCriacao();
		System.out.println(tempoPassadoMS);
		if(tempoPassadoMS < TEMPO_EXPIRAR) return false;
		else return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
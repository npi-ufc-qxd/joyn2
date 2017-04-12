package br.ufc.npi.joyn.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String fotoUrl;
	private String email;
	private String senha;

	@OneToMany
	List<ParticipacaoEvento> participacaoEvento;
	
	@OneToMany
	List<ParticipacaoAtividade> participacaoAtividade;
	
	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<ParticipacaoEvento> getParticipacaoEvento() {
		return participacaoEvento;
	}

	public void setParticipacaoEvento(List<ParticipacaoEvento> participacaoEvento) {
		this.participacaoEvento = participacaoEvento;
	}

	public List<ParticipacaoAtividade> getParticipacaoAtividade() {
		return participacaoAtividade;
	}

	public void setParticipacaoAtividade(List<ParticipacaoAtividade> participacaoAtividade) {
		this.participacaoAtividade = participacaoAtividade;
	}

}

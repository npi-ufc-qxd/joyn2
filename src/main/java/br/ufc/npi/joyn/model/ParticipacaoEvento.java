package br.ufc.npi.joyn.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ParticipacaoEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Evento evento;
	
	private boolean status;
	
	@Enumerated(EnumType.STRING)
	private Papel papel;
	
	private int pontos;
	
	public ParticipacaoEvento() {
		this.setStatus(false);
	}
	
	public ParticipacaoEvento(Usuario usuario, Evento evento, Papel papel, boolean status) {
		setUsuario(usuario);
		setEvento(evento);
		setPapel(papel);
		setStatus(status);
	}
	
	public ParticipacaoEvento(Usuario usuario, int pontos){
		setUsuario(usuario);
		setPontos(pontos);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	

}

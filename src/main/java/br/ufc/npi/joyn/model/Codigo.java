package br.ufc.npi.joyn.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Codigo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigo;

	// @OneOrTwoToOne
	@OneToOne
	private HorarioAtividade horarioAtividade;

	@ManyToOne
	private ParticipacaoAtividade participacaoAtividade;

	private Date horarioCaptura = null;

	public Codigo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public HorarioAtividade getHorarioAtividade() {
		return horarioAtividade;
	}

	public void setHorarioAtividade(HorarioAtividade horarioAtividade) {
		this.horarioAtividade = horarioAtividade;
	}

	public ParticipacaoAtividade getParticipacaoAtividade() {
		return participacaoAtividade;
	}

	public void setParticipacaoAtividade(ParticipacaoAtividade participacaoAtividade) {
		this.participacaoAtividade = participacaoAtividade;
	}

	public Date getHorarioCaptura() {
		return horarioCaptura;
	}

	public void setHorarioCaptura(Date horarioCaptura) {
		this.horarioCaptura = horarioCaptura;
	}

}

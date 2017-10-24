package br.ufc.npi.joyn.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CodigoCapturado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;
	
	@Enumerated(EnumType.STRING)
	private TipoCodigo tipo;
	
	@ManyToOne
	private ParticipacaoAtividade atividade;
	
	public CodigoCapturado() {
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

	public ParticipacaoAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(ParticipacaoAtividade atividade) {
		this.atividade = atividade;
	}

	public TipoCodigo getTipo() {
		return tipo;
	}

	public void setTipo(TipoCodigo tipo) {
		this.tipo = tipo;
	}
	
	 

}
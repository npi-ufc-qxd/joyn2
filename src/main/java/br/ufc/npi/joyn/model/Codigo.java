package br.ufc.npi.joyn.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Codigo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigo;
	
	@Enumerated
	private TipoCodigo tipo;

	@ManyToOne
	private HorarioAtividade horarioAtividade;

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

	public Date getHorarioCaptura() {
		return horarioCaptura;
	}

	public void setHorarioCaptura(Date horarioCaptura) {
		this.horarioCaptura = horarioCaptura;
	}

}

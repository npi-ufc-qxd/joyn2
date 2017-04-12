package br.ufc.npi.joyn.model;

import java.sql.Date; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class HorarioAtividade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@Temporal(TemporalType.DATE)
	private Date data;

	@OneToOne
	private Codigo checkin;
	
	@OneToOne
	private Codigo checkout;

	//@Temporal(TemporalType.TIME)
	private Date horaInicio;

	//@Temporal(TemporalType.TIME)
	private Date horaFim;

	@ManyToOne
	private Atividade atividade;

	public HorarioAtividade() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Codigo getCheckin() {
		return checkin;
	}

	public void setCheckin(Codigo checkin) {
		this.checkin = checkin;
	}

	public Codigo getCheckout() {
		return checkout;
	}

	public void setCheckout(Codigo checkout) {
		this.checkout = checkout;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

}

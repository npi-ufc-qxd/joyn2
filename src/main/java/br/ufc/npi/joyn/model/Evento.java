package br.ufc.npi.joyn.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicio;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFim;
	private String descricao;

	@OneToMany
	@JsonManagedReference
	private List<Atividade> atividades;
  
	@OneToMany
	@JsonManagedReference
	private List<ParticipacaoEvento> participantes; 
	
	private Integer vagas;
	private String local;
	private boolean status;
	private Double porcentagemMin;
	private boolean gameficado;

	public Evento() {
		this.setStatus(false);
		participantes = new ArrayList<>();
		atividades = new ArrayList<>();
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public List<ParticipacaoEvento> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<ParticipacaoEvento> participantes) {
		this.participantes = participantes;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Double getPorcentagemMin() {
		return porcentagemMin;
	}

	public void setPorcentagemMin(Double porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
	}

	public boolean isGameficado() {
		return gameficado;
	}

	public void setGameficado(boolean gameficado) {
		this.gameficado = gameficado;
	}
	
	

}

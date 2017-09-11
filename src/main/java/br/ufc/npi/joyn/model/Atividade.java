package br.ufc.npi.joyn.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.rmi.CORBA.Tie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String descricao;

	@OneToMany(cascade=CascadeType.ALL)
	private List<ParticipacaoAtividade> participantes;

	private Integer vagas;
	private Integer dias;

	@Enumerated(EnumType.STRING)
	private TiposAtividades tipo;

	private Integer minimoParaFreq;
	private Integer pontuacao;
	private boolean status;

	@ManyToOne
	private Evento evento;

	@OneToMany
	private List<HorarioAtividade> horarios;
	
	private String codeCheckin;
	
	private String codeCheckout;

	public Atividade() {
		// TODO Auto-generated constructor stub
	}
	
	public void gerarCodigos() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		String codigoCheckinStr = id + "checkin";
		codeCheckin = String.format("%032x", new BigInteger(1, md.digest(codigoCheckinStr.getBytes("UTF-8"))));
		
		if(tipo == TiposAtividades.CHECKIN_CHECKOUT){
			String codigoCheckoutStr = id + "checkout";
			codeCheckout = String.format("%032x", new BigInteger(1, md.digest(codigoCheckoutStr.getBytes("UTF-8"))));
		}
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ParticipacaoAtividade> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<ParticipacaoAtividade> participantes) {
		this.participantes = participantes;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public TiposAtividades getTipo() {
		return tipo;
	}

	public void setTipo(TiposAtividades tipo) {
		this.tipo = tipo;
	}

	public Integer getMinimoParaFreq() {
		return minimoParaFreq;
	}

	public void setMinimoParaFreq(Integer minimoParaFreq) {
		this.minimoParaFreq = minimoParaFreq;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<HorarioAtividade> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioAtividade> horarios) {
		this.horarios = horarios;
	}

	public String getCodeCheckin() {
		return codeCheckin;
	}

	public void setCodeCheckin(String codeCheckin) {
		this.codeCheckin = codeCheckin;
	}

	public String getCodeCheckout() {
		return codeCheckout;
	}

	public void setCodeCheckout(String codeCheckout) {
		this.codeCheckout = codeCheckout;
	}

}

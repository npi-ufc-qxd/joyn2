package br.ufc.npi.joyn.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CodigosTurno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Atividade atividade;
	
	private int turno;
	
	private String codigoCheckin;
	
	private String codigoCheckout;

	public CodigosTurno() {
	}
	
	public CodigosTurno(int turno) {
		this.turno = turno;
	}

	public void gerarCodigos() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		System.out.println(atividade);
		String codigoCheckinStr = atividade.getId() + "checkin" + turno;
		codigoCheckin = String.format("%032x", new BigInteger(1, md.digest(codigoCheckinStr.getBytes("UTF-8"))));
		
		if(atividade.getTipo() == TiposAtividades.CHECKIN_CHECKOUT){
			String codigoCheckoutStr = atividade.getId() + "checkout" + turno;
			codigoCheckout = String.format("%032x", new BigInteger(1, md.digest(codigoCheckoutStr.getBytes("UTF-8"))));
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public String getCodigoCheckin() {
		return codigoCheckin;
	}

	public void setCodigoCheckin(String codigoCheckin) {
		this.codigoCheckin = codigoCheckin;
	}

	public String getCodigoCheckout() {
		return codigoCheckout;
	}

	public void setCodigoCheckout(String codigoCheckout) {
		this.codigoCheckout = codigoCheckout;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

}

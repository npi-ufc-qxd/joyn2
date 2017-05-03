package br.ufc.npi.joyn.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;

public class PapelSecurity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	private Papel papel;
	
	public PapelSecurity(Papel papel) {
		this.papel = papel;
	}
	
	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + this.papel.name();
	}

}

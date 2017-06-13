package br.ufc.npi.joyn.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufc.npi.joyn.util.Constants;

@Entity
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;
	
	@Column(columnDefinition = "text", length = Constants.TAM_MAX_IMG_64)
	private String foto64;
	private String keyFacebook;

	@Email
	@NotBlank
	@Column(unique = true)
	private String email;

	@NotBlank
	private String senha;
	
	@OneToMany
	List<ParticipacaoEvento> participacaoEvento;
	
	@OneToMany
	List<ParticipacaoAtividade> participacaoAtividade;

	@Enumerated(EnumType.STRING)
	private Papel papel;

	public Usuario() {
		participacaoEvento = new ArrayList<>();
		participacaoAtividade = new ArrayList<>();
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

	public String getFoto64() {
		return foto64;
	}

	public void setFoto64(String foto64) {
		this.foto64 = foto64;
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

	public String getKeyFacebook() {
		return keyFacebook;
	}

	public void setKeyFacebook(String keyFacebook) {
		this.keyFacebook = keyFacebook;
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
	
	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new PapelSecurity(papel));
	}

	@Override
	public String getPassword() {
		return getSenha();
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

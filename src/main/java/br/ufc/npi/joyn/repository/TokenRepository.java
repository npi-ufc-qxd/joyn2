package br.ufc.npi.joyn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.Token;
import br.ufc.npi.joyn.model.Usuario;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, String>{

	Token findByUsuario(Usuario usuario);
}

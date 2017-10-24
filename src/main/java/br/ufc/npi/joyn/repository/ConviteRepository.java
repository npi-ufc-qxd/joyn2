package br.ufc.npi.joyn.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.Convite;

@Repository
@Transactional
public interface ConviteRepository extends JpaRepository<Convite, Long>{

	public Convite findByEmail(String email);
	
	public List<Convite> findByIdEvento(Long idEvento);
}

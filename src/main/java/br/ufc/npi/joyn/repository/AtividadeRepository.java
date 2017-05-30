package br.ufc.npi.joyn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.Atividade;
import java.lang.Long;

@Repository
@Transactional
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{
	
	@Query("from Atividade a where a.id = ?1")
	public Atividade getAtividade(Long id);
	
	@Query("delete from Atividade a where a.id = ?1")
	public void excluirEventoAtividade(Long id);
	
}


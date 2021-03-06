package br.ufc.npi.joyn.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.Papel;
import br.ufc.npi.joyn.model.ParticipacaoEvento;

@Repository
@Transactional
public interface ParticipacaoEventoRepository extends JpaRepository<ParticipacaoEvento, Long> {
	
	@Query("select pe.papel from ParticipacaoEvento pe where pe.usuario.id = ?1 and pe.evento.id = ?2")
	public Papel getPapelUsuarioEvento(Long usuarioId, Long eventoId);
	
	@Query("select pe from ParticipacaoEvento pe where pe.evento.id = ?1 and pe.papel = 'PARTICIPANTE'")
	List<ParticipacaoEvento> getListaParticipantes(Long id);
	
	@Query("select pe from ParticipacaoEvento pe where pe.evento.id = ?1 and pe.papel = 'ORGANIZADOR'")
	List<ParticipacaoEvento> getListaOrganizadores(Long id);

}

package br.ufc.npi.joyn.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.Evento;

@Repository
@Transactional
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	@Query("select e "
			+ "from Evento e, ParticipacaoEvento p "
			+ "where e.id = p.evento.id and p.usuario.id = ?1 and p.papel = 'ORGANIZADOR'")
	List<Evento> meusEventos(Long usuarioId);
	
	@Query("select e "
			+ "from Evento e, ParticipacaoEvento p "
			+ "where e.id = p.evento.id and p.usuario.id = ?1 and p.papel = 'ORGANIZADOR'"
			+ "and upper(e.nome) like %?2%")
	List<Evento> meusEventosPorNome(Long usuarioId, String nome);

}

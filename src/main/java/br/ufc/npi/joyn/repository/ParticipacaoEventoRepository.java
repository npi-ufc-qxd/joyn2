package br.ufc.npi.joyn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joyn.model.ParticipacaoEvento;

@Repository
@Transactional
public interface ParticipacaoEventoRepository extends JpaRepository<ParticipacaoEvento, Long> {

}

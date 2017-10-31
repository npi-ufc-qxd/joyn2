package br.ufc.npi.joyn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.CodigosTurno;
import br.ufc.npi.joyn.repository.CodigosTurnoRepository;

@Service
public class CodigosTurnoService {
	
	@Autowired
	private CodigosTurnoRepository codigoRepo;
	
	public void salvar(CodigosTurno codigoTurno){
		codigoRepo.save(codigoTurno);
	}

	public void deletarTurnos(List<CodigosTurno> codigosTurno) {
		for(CodigosTurno ct: codigosTurno) 
			codigoRepo.delete(ct);
	}
}

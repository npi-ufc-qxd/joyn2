package br.ufc.npi.joyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joyn.model.Convite;
import br.ufc.npi.joyn.repository.ConviteRepository;

@Service
public class ConviteService {

	@Autowired
	ConviteRepository conviteRepository;
	
	public void addConvite(Convite convite){
		conviteRepository.save(convite);
	}
	
	public Convite getConvite(String email){
		return conviteRepository.findByEmail(email);
	}
}

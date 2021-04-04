package com.michel.tcp.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michel.tcp.model.Echantillon;
import com.michel.tcp.repository.EchantillonRepo;
import com.michel.tcp.service.IEchantillonService;

@Service
public class EchantillonService implements IEchantillonService{
	
	@Autowired
	EchantillonRepo echantillonRepo;

	public void enregistrerEchantillon(Echantillon echantillon) {
		
		echantillonRepo.save(echantillon);
		
	}

	public Echantillon obtenirEchantillonParId(Integer id) {
		
		Echantillon echantillon = echantillonRepo.getOne(id);
		return echantillon;
	}

	

}

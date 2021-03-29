package com.michel.tcp.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michel.tcp.model.Seance;
import com.michel.tcp.repository.SeanceRepo;
import com.michel.tcp.service.ISeanceService;

@Service
public class SeanceService implements ISeanceService {
	
	@Autowired
	SeanceRepo seanceRepo;

	public void enregistrerSeance(Seance seance) {
		
		seanceRepo.save(seance);
		
	}

	public Seance obtenirSeanceParId(Integer id) {
		
		Seance seance = seanceRepo.getOne(id);
		return seance;
	}

	public List<Seance> obtenirSeancesInanctives() {
		
		List<Seance> seancesInactives = seanceRepo.findByActif(false);
		
		return seancesInactives;
	}

	public List<Seance>  obtenirSeanceActive() {
		
		List<Seance> seances = seanceRepo.findByActif(true);
		return seances;
		
	}

}

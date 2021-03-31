package com.michel.tcp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.michel.tcp.repository.SeanceRepo;
import com.michel.tcp.service.jpa.SeanceService;

@Component
public class BaseHandler {
	
	@Autowired
	private SeanceRepo seanceRepo;
	
	
	
	public BaseHandler() {
		super();
		// TODO Auto-generated constructor stub
	}



	public BaseHandler(SeanceRepo seanceRepo) {
		super();
		this.seanceRepo = seanceRepo;
	}



	Seance obtenirSeanceParId(Integer id) {
		
		Seance seance = seanceRepo.getOne(id);
		
		return seance;
	}
	
	
	
	
}

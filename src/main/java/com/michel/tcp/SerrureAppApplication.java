package com.michel.tcp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import com.michel.tcp.model.Commande;
import com.michel.tcp.model.Echantillon;
import com.michel.tcp.model.Rapport;
import com.michel.tcp.model.Seance;
import com.michel.tcp.service.jpa.SeanceService;
import com.michel.tcp.socket.ServerTcp;

@SpringBootApplication

public class SerrureAppApplication {
	
	
	public static boolean disconnectRequest = false;
	public static Commande commande = new Commande();     // ordre envoyé vers pi
	public static Rapport rapport = new Rapport();     // rapport reçu de pi
	public static Integer id; // id scéance en cours
	
	public static  Seance seance = new Seance();
	public static List<Echantillon> echantillons = new ArrayList<Echantillon>();
	public static Echantillon echantillon1 = new Echantillon();
	public static Echantillon echantillon2 = new Echantillon();
	public static Echantillon echantillon3 = new Echantillon();
	
	@Autowired
	public static SeanceService seanceService;
	
	public static void main(String[] args) {
		
		SpringApplication.run(SerrureAppApplication.class, args);
		
		echantillon1.setSeance(seance);
		echantillon2.setSeance(seance);
		echantillon3.setSeance(seance);
		
		echantillons.add(echantillon1);
		echantillons.add(echantillon2);
		echantillons.add(echantillon3);
		
		int port = 5725;
	
		ServerTcp ts = new ServerTcp(port, 100);
		ts.open();

		System.out.println("INFO$: Serveur initialisé.");

	}

}

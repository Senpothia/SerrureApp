package com.michel.tcp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.michel.tcp.model.BaseHandler;
import com.michel.tcp.model.Commande;
import com.michel.tcp.model.Contexte;
import com.michel.tcp.model.Echantillon;
import com.michel.tcp.model.Rapport;
import com.michel.tcp.model.Seance;

import com.michel.tcp.service.IEchantillonService;
import com.michel.tcp.service.jpa.SeanceService;
import com.michel.tcp.socket.ServerTcp;

@SpringBootApplication

public class SerrureAppApplication {
	
	
	public static boolean disconnectRequest = false;
	public static Commande commande = new Commande();     // ordre envoyé vers pi
	public static Rapport rapport = new Rapport();    	  // rapport reçu de pi
	public static Integer id; 							  // id scéance en cours
	public static Contexte contexte = new Contexte();
	
	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = SpringApplication.run(SerrureAppApplication.class, args);
		
		int port = 6239;
	
		ServerTcp ts = new ServerTcp(port, 100); //, applicationContext);
		ts.open();


	}




}

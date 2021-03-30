package com.michel.tcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.michel.tcp.socket.ServerTcp;

@SpringBootApplication
public class SerrureAppApplication {

	public static boolean disconnectRequest = false;

	public static void main(String[] args) {
		SpringApplication.run(SerrureAppApplication.class, args);
		
		int port = 5725;
	
		ServerTcp ts = new ServerTcp(port, 100);
		ts.open();

		System.out.println("INFO$: Serveur initialisé.");

	}

}

package com.michel.tcp.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Centrale implements Runnable{
	
	private List<Socket> clients;
	private int port = 5725;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	
	
	public Centrale(List<Socket> clients, int port) {
		super();
		this.clients = clients;
		this.port = port;
	}

	@Override
	public void run() {
	
		ServerSocket server = null;
		Socket sc = null;
		
		try {
			
			server = new ServerSocket(port);
			System.out.println("INFO$: Serveur lancé");
			
			while(true) {
				
				
				sc = server.accept();
				System.out.println("INFO$: Nouveau client connecté");
				clients.add(sc);
				
				writer = new PrintWriter(sc.getOutputStream());
				InputStreamReader inr = new InputStreamReader(sc.getInputStream());
				BufferedReader br = new BufferedReader(inr);
				
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	
		
	}

}

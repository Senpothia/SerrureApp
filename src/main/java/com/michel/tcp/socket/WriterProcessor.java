package com.michel.tcp.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;

import com.michel.tcp.SerrureAppApplication;
import com.michel.tcp.service.jpa.EchantillonService;


public class WriterProcessor implements Runnable {

	
	private Socket mySocket;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	
	@Autowired
	EchantillonService echantillonService;

	public WriterProcessor(Socket socket) {
		
		this.mySocket = socket;
	}

	@Override
	public void run() {

		System.out.println("INFO$: Lancement du traitement des transferts vers le client");
		boolean closeConnexion = false;

		while (!mySocket.isClosed()) {

			try {
				writer = new PrintWriter(mySocket.getOutputStream());
			
				if(SerrureAppApplication.rapport.isChanged()) {
					
					/* test
					writer.println("ACQ");	
					writer.flush();
					SerrureAppApplication.rapport.setChanged(false);
					*/
					
					// A faire: traitement du rapport reçu

				}
				
				if(SerrureAppApplication.commande.isChanged()) {  // Transmission de la commande vers pi
					
					String commande = SerrureAppApplication.commande.getMessage();
					writer.println(commande);	
					writer.flush();
					SerrureAppApplication.commande.setChanged(false);
					
				}
				
				
				
				if (SerrureAppApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					writer = null;
					reader = null;
					mySocket.close();
					SerrureAppApplication.disconnectRequest = false;
					
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	

	}

}

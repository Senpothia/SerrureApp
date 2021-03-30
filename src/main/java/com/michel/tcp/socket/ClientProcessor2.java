package com.michel.tcp.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.michel.tcp.SerrureAppApplication;

public class ClientProcessor2 implements Runnable {

	private Socket mySocket;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;

	public ClientProcessor2(Socket socket) {
		
		this.mySocket = socket;
	}

	// Le traitement
	public void run() {

		System.out.println("INFO$: Lancement du traitement des transferts vers le client");
		boolean closeConnexion = false;

		while (!mySocket.isClosed()) {

			try {
				writer = new PrintWriter(mySocket.getOutputStream());
				//InputStreamReader inr = new InputStreamReader(mySocket.getInputStream());
				//BufferedReader br = new BufferedReader(inr);
				
				if(SerrureAppApplication.ordre.isChanged())
				
				writer.println("ACQ");	
				writer.flush();
				SerrureAppApplication.ordre.setChanged(false);

				/*
				 * 
				 * if (SerrureAppApplication.chaine.isChange()) {
				 * 
				 * 
				 * 
				 * 
				 * writer.println(SerrureAppApplication.chaine.getMessage());
				 * writer.flush();
				 * SerrureAppApplication.chaine.setChange(false);
				 * SerrureAppApplication.chaine.setLecture(false); }
				 * 
				 */
				if (SerrureAppApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					writer = null;
					reader = null;
					mySocket.close();
					SerrureAppApplication.disconnectRequest = false;
					// WebAppSocketApplication.connexions.remove(connexion);
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Tant que la connexion est active

	}

}

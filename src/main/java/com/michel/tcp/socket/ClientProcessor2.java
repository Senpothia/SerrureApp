package com.michel.tcp.socket;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.attoparser.trace.TraceBuilderMarkupHandler;

import com.michel.tcp.SerrureAppApplication;

import java.io.InputStreamReader;
import java.io.BufferedReader;


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
				InputStreamReader inr = new InputStreamReader(mySocket.getInputStream());
				BufferedReader br = new BufferedReader(inr);
				
			
					writer.flush();
				
				
					/*

				if (SerrureAppApplication.chaine.isChange()) {
					
					
				
					
					writer.println(SerrureAppApplication.chaine.getMessage());
					writer.flush();
					SerrureAppApplication.chaine.setChange(false);
					SerrureAppApplication.chaine.setLecture(false);
				}
				
*/
				if (SerrureAppApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					writer = null;
					reader = null;
					mySocket.close();
					SerrureAppApplication.disconnectRequest = false;
					//WebAppSocketApplication.connexions.remove(connexion);
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

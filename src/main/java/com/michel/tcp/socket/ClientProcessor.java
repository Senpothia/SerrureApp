package com.michel.tcp.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.michel.tcp.SerrureAppApplication;

public class ClientProcessor implements Runnable {

	private Socket mySocket;
	
	private BufferedInputStream reader = null;

	public ClientProcessor(Socket socket) {
		this.mySocket = socket;

	}

	@Override
	public void run() {
		System.out.println("INFO$: Lancement du traitement des reception depuis le client");
		boolean closeConnexion = false;

		// Tant que la connexion est active
		while (!mySocket.isClosed()) {
			try {

			
				InputStreamReader inr = new InputStreamReader(mySocket.getInputStream());
				BufferedReader br = new BufferedReader(inr);
				String reception = br.readLine();

				System.out.println("Ordre réçu: " + reception);
				SerrureAppApplication.ordre.setMessage(reception);
				SerrureAppApplication.ordre.setChanged(true);
				

				//parseCode(reception);

				if (reception == null) {

					mySocket.close();

				}

				if (SerrureAppApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					
					reader = null;
					mySocket.close();
					SerrureAppApplication.disconnectRequest = false;
					
				}

			} catch (SocketException e) {
				System.err.println("INFO$: LA CONNEXION A ETE INTERROMPUE !");

			} catch (IOException e) {
				e.printStackTrace();
			}

		} // fin while

		System.out.println("Sortie");

		
	}

	public void parseCode(String message) {

		try {

			if (message.toUpperCase().startsWith("C:<") && message.endsWith(">")) {// ****

				try {
					String sub = message.substring(3);

					String recu = sub.replace('<', ' ');

					String[] strings = recu.split(" ");
					String IMEI = strings[0];
					IMEI = IMEI.replace(">", "");

					String IMSI = strings[1];
					IMSI = IMSI.replace(">", "");

				

				} catch (Exception e) {

				

				}

				

			} else {

			

			} 

		} catch (Exception e) {

			try {
				mySocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	
	}

}

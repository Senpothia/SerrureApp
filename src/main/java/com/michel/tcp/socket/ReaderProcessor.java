package com.michel.tcp.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.michel.tcp.SerrureAppApplication;
import com.michel.tcp.model.Seance;
import com.michel.tcp.service.jpa.SeanceService;

public class ReaderProcessor implements Runnable {

	private Socket mySocket;
	private BufferedInputStream reader = null;

	public ReaderProcessor(Socket socket) {
		
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
				String reception = br.readLine(); // Lecture

				System.out.println("Rapport réçu: " + reception);
				SerrureAppApplication.rapport.setMessage(reception);
				SerrureAppApplication.rapport.setChanged(true);

				parseCode(reception);

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

			if (message.startsWith("pi:>") && message.endsWith("#")) {// ****

				try {

					String sub = message.substring(4);
					String[] strings = sub.split(">");

					for (String retval : sub.split(">")) {
						System.out.println(retval);
						
					}
					
				SerrureAppApplication.seance.setEtat(strings[0]);
				SerrureAppApplication.seance.setActif(Boolean.parseBoolean(strings[1]));
				
				SerrureAppApplication.echantillons.get(0).setCompteur(Long.parseLong(strings[2]));
				SerrureAppApplication.echantillons.get(0).setActif(Boolean.parseBoolean(strings[3]));
				SerrureAppApplication.echantillons.get(0).setErreur(Boolean.parseBoolean(strings[4]));
				SerrureAppApplication.echantillons.get(0).setPause(Boolean.parseBoolean(strings[5]));		
				SerrureAppApplication.echantillons.get(0).setInterrompu(Boolean.parseBoolean(strings[6]));		
						
				SerrureAppApplication.echantillons.get(1).setCompteur(Long.parseLong(strings[7]));
				SerrureAppApplication.echantillons.get(1).setActif(Boolean.parseBoolean(strings[8]));
				SerrureAppApplication.echantillons.get(1).setErreur(Boolean.parseBoolean(strings[9]));
				SerrureAppApplication.echantillons.get(1).setPause(Boolean.parseBoolean(strings[10]));		
				SerrureAppApplication.echantillons.get(1).setInterrompu(Boolean.parseBoolean(strings[11]));	
				
				SerrureAppApplication.echantillons.get(2).setCompteur(Long.parseLong(strings[12]));
				SerrureAppApplication.echantillons.get(2).setActif(Boolean.parseBoolean(strings[13]));
				SerrureAppApplication.echantillons.get(2).setErreur(Boolean.parseBoolean(strings[14]));
				SerrureAppApplication.echantillons.get(2).setPause(Boolean.parseBoolean(strings[15]));		
				SerrureAppApplication.echantillons.get(2).setInterrompu(Boolean.parseBoolean(strings[16]));		

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

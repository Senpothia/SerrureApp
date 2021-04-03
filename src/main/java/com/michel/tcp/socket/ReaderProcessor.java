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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.michel.tcp.SerrureAppApplication;
import com.michel.tcp.model.Seance;
import com.michel.tcp.service.jpa.SeanceService;

/*
@Component
@Scope("prototype")
*/
public class ReaderProcessor implements Runnable {

	private Socket mySocket;
	private BufferedInputStream reader = null;

	public ReaderProcessor(Socket socket) {
		
		this.mySocket = socket;
		
	}
	
	
	public Socket getMySocket() {
		return mySocket;
	}


	public void setMySocket(Socket mySocket) {
		this.mySocket = mySocket;
	}


	public BufferedInputStream getReader() {
		return reader;
	}


	public void setReader(BufferedInputStream reader) {
		this.reader = reader;
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
			

			} catch (IOException e) {
				e.printStackTrace();
			}

		} // fin while

		System.out.println("Sortie");

	}

	public void parseCode(String message) {

		try {

			if (message.startsWith("pi:>") && message.endsWith("#")) {// ****
				
				System.out.println("Match parsing!");
				try {

					String sub = message.substring(4);
					String[] strings = sub.split(">");

					for (String retval : sub.split(">")) {
						System.out.println(retval);
						
					}
					
				SerrureAppApplication.contexte.getSeance().setEtat(strings[0]);
				SerrureAppApplication.contexte.getSeance().setActif(Boolean.parseBoolean(strings[1]));
				
				SerrureAppApplication.contexte.getEchantillon1().setCompteur(Long.parseLong(strings[2]));
				
				SerrureAppApplication.contexte.getEchantillon1().setActif(Boolean.parseBoolean(strings[3]));
				SerrureAppApplication.contexte.getEchantillon1().setErreur(Boolean.parseBoolean(strings[4]));
				SerrureAppApplication.contexte.getEchantillon1().setPause(Boolean.parseBoolean(strings[5]));		
				SerrureAppApplication.contexte.getEchantillon1().setInterrompu(Boolean.parseBoolean(strings[6]));		
						
				SerrureAppApplication.contexte.getEchantillon2().setCompteur(Long.parseLong(strings[7]));
				SerrureAppApplication.contexte.getEchantillon2().setActif(Boolean.parseBoolean(strings[8]));
				SerrureAppApplication.contexte.getEchantillon2().setErreur(Boolean.parseBoolean(strings[9]));
				SerrureAppApplication.contexte.getEchantillon2().setPause(Boolean.parseBoolean(strings[10]));		
				SerrureAppApplication.contexte.getEchantillon2().setInterrompu(Boolean.parseBoolean(strings[11]));	
				
				SerrureAppApplication.contexte.getEchantillon3().setCompteur(Long.parseLong(strings[12]));
				SerrureAppApplication.contexte.getEchantillon3().setActif(Boolean.parseBoolean(strings[13]));
				SerrureAppApplication.contexte.getEchantillon3().setErreur(Boolean.parseBoolean(strings[14]));
				SerrureAppApplication.contexte.getEchantillon3().setPause(Boolean.parseBoolean(strings[15]));		
				SerrureAppApplication.contexte.getEchantillon3().setInterrompu(Boolean.parseBoolean(strings[16]));
				
				
				SerrureAppApplication.contexte.setChanged(true);
				System.out.println("Changed: " + 	SerrureAppApplication.contexte.getChanged());

				} catch (Exception e) {
					
					System.err.println("Défaut parser!");

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

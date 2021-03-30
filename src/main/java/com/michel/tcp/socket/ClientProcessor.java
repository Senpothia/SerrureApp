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

import com.michel.tcp.SerrureAppApplication;

import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ClientProcessor implements Runnable {
	
	private Socket mySocket;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	
	public ClientProcessor(Socket socket) {
		this.mySocket = socket;
		
	}

	// Le traitement
	public void run() {
		System.out.println("INFO$: Lancement du traitement de la connexion d'un client");
		boolean closeConnexion = false;
		
	
		
		// Tant que la connexion est active
		while (!mySocket.isClosed()) {
			try {
				
					writer = new PrintWriter(mySocket.getOutputStream());
					InputStreamReader inr = new InputStreamReader(mySocket.getInputStream());
					BufferedReader br = new BufferedReader(inr);
					String response = br.readLine();
					
			
				if (SerrureAppApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					writer = null;
					reader = null;
					mySocket.close();
					SerrureAppApplication.disconnectRequest = false;
					//WebAppSocketApplication.connexions.remove(connexion);
					
				}

			} catch (SocketException e) {
				System.err.println("INFO$: LA CONNEXION A ETE INTERROMPUE !");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			

		} // fin while

		System.out.println("Sortie");
	
		// return;
	}

	

	
	public String parseCode2(String response, Socket mySocket) {
		
		String toSend = "";
	
		
		try {
			
		
					
					if (response.toUpperCase().startsWith("C:<") && response.endsWith(">")) {//****

						try {
							String sub = response.substring(3);

							String recu = sub.replace('<', ' ');

							String[] strings = recu.split(" ");
							String IMEI = strings[0];
							IMEI = IMEI.replace(">", "");
							

							String IMSI = strings[1];
							IMSI = IMSI.replace(">", "");

							long ei = Long.parseLong(IMEI);
							long si = Long.parseLong(IMSI);

						

							System.out.println("SERVEUR$: IMEI = " + IMEI);
							System.out.println("SERVEUR$: IMSI = " + IMSI);

							boolean match = false;
							int j = 0;

							while (!match ) {

								
							
									//toSend = "OK";
									

							
							}

							if (!match) {

								System.out.println("Aucun code enregistré trouvé!");
								toSend = "ERROR";
								
							}

						} catch (Exception e) {

						//	toSend = "Syntax Error !";

						}

						//WebAppSocketApplication.connexions.add(connexion);

					} else {

					//	toSend = "Syntax Error !";
						
					}//********
					
					/*
				}
				
			
				break;
			}   // fin default
			}
*/
		} catch (Exception e) {

		
			try {
				mySocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	
		//WebAppSocketApplication.connexions.add(connexion);
		return toSend;
	}

}

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



import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ClientProcessor implements Runnable, Observer {
	
	private Socket mySocket;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private String IMEI = "";
	private String IMSI = "";
	
	private LocalDateTime date;

	public ClientProcessor(Socket socket) {
		this.mySocket = socket;
		
	}

	// Le traitement
	public void run() {
		System.out.println("INFO$: Lancement du traitement de la connexion d'un client");
		boolean closeConnexion = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	//	WebAppSocketApplication.connexions.add(connexion);
		
		// Tant que la connexion est active
		while (!mySocket.isClosed()) {
			try {
				
					writer = new PrintWriter(mySocket.getOutputStream());
					InputStreamReader inr = new InputStreamReader(mySocket.getInputStream());
					BufferedReader br = new BufferedReader(inr);
					String response = br.readLine();
					
					if (!WebAppSocketApplication.chaine.isLecture()) {
						
						Transfert transfert = new Transfert();
						transfert.setEnvoi(LocalDateTime.now().format(formatter));
						transfert.setCommande(WebAppSocketApplication.chaine.getMessage());
						System.out.println("Acquittement reçu: " + response);
						LocalDateTime date = LocalDateTime.now();
					
						transfert.setAcquittement(response);
						transfert.setRetour(LocalDateTime.now().format(formatter));
						WebAppSocketApplication.transferts.add(transfert);
						System.out.println("taille liste transferts: " + WebAppSocketApplication.transferts.size());
						WebAppSocketApplication.chaine.setLecture(true);

					}else {
												
						System.out.println("INFO$: Message reçu du client: " + response);

						// On affiche quelques infos, pour le débuggage
						//getInfoConnexion(mySocket, response);

						// On traite la demande du client et on lui repond
						String toSend = "";
						
						toSend = parseCode2(response, mySocket);
						
						System.out.println("INFO$: toSend = " + toSend);
						// On envoie la reponse au client
						if (!toSend.equals("")) {
							
							writer.println(toSend);
							writer.flush();
							
						}
						
											
					}
				
				if (WebAppSocketApplication.disconnectRequest) {
					System.err.println("INFO$: COMMANDE CLOSE DETECTEE!");
					writer = null;
					reader = null;
					mySocket.close();
					WebAppSocketApplication.disconnectRequest = false;
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

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

							while (!match && j < WebAppSocketApplication.abonnes.size()) {

								Imei i = WebAppSocketApplication.abonnes.get(j);
								long c = i.getCode();
								if (c == ei) {

									toSend = "OK";
									match = true;
								

								}

								j++;
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

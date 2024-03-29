package com.michel.tcp.socket;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.ApplicationContext;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class ServerTcp {

	private int port = 6239;
	private int maxSockets = 10;
	private ServerSocket server = null;
	private boolean isRunning = true;

	//private ApplicationContext applicationContext;

	public ServerTcp() {
		try {
			server = new ServerSocket(port, maxSockets);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerTcp(int bindPort, int maxClientConnexions) {  //, ApplicationContext applicationContext) {
		this.port = bindPort;
		this.maxSockets = maxClientConnexions;
		//this.applicationContext = applicationContext;
		try {
			server = new ServerSocket(this.port, this.maxSockets);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Pour lancer le serveur
	public void open() {
		// On lance le serveur sur un thread à part car il a une boucle infine
		Thread serverThread = new Thread(new Runnable() { // Définition du Thread
			public void run() {

				while (isRunning == true) {
					try {
						// On attend une connexion d'un client
						Socket clientSocket = server.accept();

						// *****************************

						// On affiche quelques infos, pour le débuggage
						InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();

						// ****************************

						// Une fois reçu, on traite l'echange avec ce nouveau client dans un nouveau
						// thread
						
						  Thread newClientThread = new Thread(new ReaderProcessor(clientSocket));
						  newClientThread.start(); 
						  Thread newClientThread2 = new Thread(new
						  WriterProcessor(clientSocket)); newClientThread2.start();
						 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// On ferme le secket du serveur
				try {

					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		});

		serverThread.start(); // lancement du Thread
	}

	// Pour arreter le serveur
	public void close() {
		this.isRunning = false;
	}

}

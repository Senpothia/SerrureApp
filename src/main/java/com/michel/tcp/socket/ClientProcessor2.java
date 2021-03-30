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

	@Override
	public void run() {

		System.out.println("INFO$: Lancement du traitement des transferts vers le client");
		boolean closeConnexion = false;

		while (!mySocket.isClosed()) {

			try {
				writer = new PrintWriter(mySocket.getOutputStream());
			
				if(SerrureAppApplication.ordre.isChanged()) {
					
					writer.println("ACQ");	
					writer.flush();
					SerrureAppApplication.ordre.setChanged(false);

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

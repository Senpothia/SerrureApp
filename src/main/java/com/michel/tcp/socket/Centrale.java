package com.michel.tcp.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.michel.tcp.SerrureAppApplication;

public class Centrale implements Runnable{
	
	private Socket socket;
	private int port = 5725;

	
	
	public Centrale(Socket socket, int port) {
		super();
		this.socket = socket;
		this.port = port;
	}

	@Override
	public void run() {
	
		while(!socket.isClosed()) {
			
			
			
			
		}
		
	
		
	}

}

package com.michel.tcp.model;

public class Ordre {
	
	public String message;
	public boolean changed = false;
	public String commande;
	
	public Ordre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ordre(String message, boolean changed) {
		super();
		this.message = message;
		this.changed = changed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	
	
	

}

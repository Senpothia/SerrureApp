package com.michel.tcp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.OneToMany;

public class ProtoSeance {
	
	private Integer id;   // id de la seance
	private String description;
	private LocalDateTime date;
	private String etat;  // marche, arret, pause
	private Boolean actif; // en cours, terminée
	
	public ProtoSeance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProtoSeance(Integer id, String description, LocalDateTime date, String etat, Boolean actif) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.etat = etat;
		this.actif = actif;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	
	
	
	

}

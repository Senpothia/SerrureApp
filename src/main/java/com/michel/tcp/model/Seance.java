package com.michel.tcp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Seance {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToMany(mappedBy = "seance")
	private List<Echantillon> echantillons;
	
	private String etat;  // marche, arret, pause
	private Boolean actif; // en cours, terminée
	
	public Seance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seance(List<Echantillon> echantillons, String etat, Boolean actif) {
		super();
		this.echantillons = echantillons;
		this.etat = etat;
		this.actif = actif;
	}

	public List<Echantillon> getEchantillons() {
		return echantillons;
	}

	public void setEchantillons(List<Echantillon> echantillons) {
		this.echantillons = echantillons;
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

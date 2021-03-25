package com.michel.tcp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Echantillon {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Seance seance;
	
	private String type; // DX200; APX200
	private int position;
	private Boolean actif;
	private Boolean erreur;
	private Boolean pause;
	private Boolean interrompu;
	private Long compteur;
	
	public Echantillon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Echantillon(Seance seance, String type, int position, Boolean actif, Boolean erreur, Boolean pause,
			Boolean interrompu, Long compteur) {
		super();
		this.seance = seance;
		this.type = type;
		this.position = position;
		this.actif = actif;
		this.erreur = erreur;
		this.pause = pause;
		this.interrompu = interrompu;
		this.compteur = compteur;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Boolean getErreur() {
		return erreur;
	}

	public void setErreur(Boolean erreur) {
		this.erreur = erreur;
	}

	public Boolean getPause() {
		return pause;
	}

	public void setPause(Boolean pause) {
		this.pause = pause;
	}

	public Boolean getInterrompu() {
		return interrompu;
	}

	public void setInterrompu(Boolean interrompu) {
		this.interrompu = interrompu;
	}

	public Long getCompteur() {
		return compteur;
	}

	public void setCompteur(Long compteur) {
		this.compteur = compteur;
	}

	
	
	
}

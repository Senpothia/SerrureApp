package com.michel.tcp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.michel.tcp.model.auxiliaire.FormSeance;

@Entity
public class Echantillon {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private Seance seance;

	private String type; // DX200; APX200
	private int position;  // 1, 2, ou 3 sur l'interface de test
	private Boolean actif; // sélectionné pour le test
	private Boolean erreur;  // mise en défaut en cours de test
	private Boolean pause;   // suspension momentanée de l'essai
	private Boolean interrompu;  // test stoppé
	private Long compteur;       // nbre de cycle de test effectué

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

	public Echantillon(FormSeance formSeance, Seance seance, int position) {

		String typeEchantillon = null;
		Long compteur = null;
		Boolean actif = null;

		switch (position) {

		case 1:

			typeEchantillon = formSeance.getType1();
			compteur = formSeance.getCompteur1();
			actif = formSeance.getActif1();
			break;

		case 2:

			typeEchantillon = formSeance.getType2();
			compteur = formSeance.getCompteur2();
			actif = formSeance.getActif2();
			break;

		case 3:

			typeEchantillon = formSeance.getType3();
			compteur = formSeance.getCompteur3();
			actif = formSeance.getActif3();
			break;

		}

		

		this.seance = seance;
		this.type = typeEchantillon;
		this.position = position;
		this.actif = actif;
		this.erreur = false;
		this.pause = true;
		this.interrompu = false;
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

	@Override
	public String toString() {
		return "Echantillon [id=" + id + ", seance=" + seance + ", type=" + type + ", position=" + position + ", actif="
				+ actif + ", erreur=" + erreur + ", pause=" + pause + ", interrompu=" + interrompu + ", compteur="
				+ compteur + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
	
	

}

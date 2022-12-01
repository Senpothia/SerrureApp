package com.michel.tcp.model;

public class ProtoEchantillon {
	
	private Integer id;  // id de l'échantillon
	private String type; // DX200; APX200
	private int position;  // 1, 2, ou 3 sur l'interface de test
	private Boolean actif; // sélectionné pour le test
	private Boolean erreur;  // mise en défaut en cours de test
	private Boolean pause;   // suspension momentanée de l'essai
	private Boolean interrompu;  // test stoppé
	private Long compteur; // nbre de cycle de test effectué
	private Integer seance; // id de la seance
	
	public ProtoEchantillon() {
		 super();
		// TODO Auto-generated constructor stub
	}

	public ProtoEchantillon(Integer id, String type, int position, Boolean actif, Boolean erreur, Boolean pause,
			Boolean interrompu, Long compteur, Integer seance) {
		super();
		this.id = id;
		this.type = type;
		this.position = position;
		this.actif = actif;
		this.erreur = erreur;
		this.pause = pause;
		this.interrompu = interrompu;
		this.compteur = compteur;
		this.seance = seance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getSeance() {
		return seance;
	}

	public void setSeance(Integer seance) {
		this.seance = seance;
	}
	
	
	
}

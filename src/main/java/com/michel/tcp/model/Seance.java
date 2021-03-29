package com.michel.tcp.model;

import java.time.LocalDateTime;
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
	
	private String description;
	private LocalDateTime date;
	private String etat;  // marche, arret, pause
	private Boolean actif; // en cours, terminée
	
	public Seance() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Seance(Integer id, List<Echantillon> echantillons, String description, LocalDateTime date, String etat,
			Boolean actif) {
		super();
		this.id = id;
		this.echantillons = echantillons;
		this.description = description;
		this.date = date;
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



	@Override
	public String toString() {
		return "Seance [id=" + id + ", echantillons=" + echantillons + ", description=" + description + ", date=" + date
				+ ", etat=" + etat + ", actif=" + actif + "]";
	}
	
	
	
	

}

package com.michel.tcp.model.auxiliaire;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.michel.tcp.model.Echantillon;
import com.michel.tcp.model.Seance;

public class FormSeance {

	private Integer id;
	private String Description;
	private String date;
	private String type1;
	private String type2;
	private String type3;
	private Boolean actif;
	private Boolean actif1;
	private Boolean actif2;
	private Boolean actif3;
	private Boolean erreur1;
	private Boolean erreur2;
	private Boolean erreur3;
	private Integer position1;
	private Integer position2;
	private Integer position3;
	private Boolean interrompu1;
	private Boolean interrompu2;
	private Boolean interrompu3;
	private Boolean pause1;
	private Boolean pause2;
	private Boolean pause3;
	private Long compteur1;
	private Long compteur2;
	private Long compteur3;

	public FormSeance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormSeance(Integer id, String description, String date, String type1, String type2, String type3,
			Boolean actif1, Boolean actif2, Boolean actif3, Long compteur1, Long compteur2, Long compteur3) {
		super();
		this.id = id;
		Description = description;
		this.date = date;
		this.type1 = type1;
		this.type2 = type2;
		this.type3 = type3;
		this.actif1 = actif1;
		this.actif2 = actif2;
		this.actif3 = actif3;
		this.compteur1 = compteur1;
		this.compteur2 = compteur2;
		this.compteur3 = compteur3;
	}

	public FormSeance(Seance seance) {

		super();
		this.id = seance.getId();
		this.Description = seance.getDescription();
		this.date = seance.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		List<Echantillon> echantillons = seance.getEchantillons();
		Collections.sort(echantillons);
		this.type1 = echantillons.get(0).getType();
		this.type2 = echantillons.get(1).getType();
		this.type3 = echantillons.get(2).getType();
		this.actif1 = echantillons.get(0).getActif();
		this.actif2 = echantillons.get(1).getActif();
		this.actif3 = echantillons.get(2).getActif();
		this.compteur1 = echantillons.get(0).getCompteur();
		this.compteur2 = echantillons.get(1).getCompteur();
		this.compteur3 = echantillons.get(2).getCompteur();
		this.erreur1 = echantillons.get(0).getErreur();
		this.erreur2 = echantillons.get(1).getErreur();
		this.erreur3 = echantillons.get(2).getErreur();
		this.position1 = echantillons.get(0).getPosition();
		this.position2 = echantillons.get(1).getPosition();
		this.position3 = echantillons.get(2).getPosition();
		this.interrompu1 = echantillons.get(0).getInterrompu();
		this.interrompu2 = echantillons.get(1).getInterrompu();
		this.interrompu3 = echantillons.get(2).getInterrompu();
		this.pause1 = echantillons.get(0).getPause();
		this.pause2 = echantillons.get(1).getPause();
		this.pause3 = echantillons.get(2).getPause();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public Boolean getActif1() {
		return actif1;
	}

	public void setActif1(Boolean actif1) {
		this.actif1 = actif1;
	}

	public Boolean getActif2() {
		return actif2;
	}

	public void setActif2(Boolean actif2) {
		this.actif2 = actif2;
	}

	public Boolean getActif3() {
		return actif3;
	}

	public void setActif3(Boolean actif3) {
		this.actif3 = actif3;
	}

	public Long getCompteur1() {
		return compteur1;
	}

	public void setCompteur1(Long compteur1) {
		this.compteur1 = compteur1;
	}

	public Long getCompteur2() {
		return compteur2;
	}

	public void setCompteur2(Long compteur2) {
		this.compteur2 = compteur2;
	}

	public Long getCompteur3() {
		return compteur3;
	}

	public void setCompteur3(Long compteur3) {
		this.compteur3 = compteur3;
	}

	public Boolean getErreur1() {
		return erreur1;
	}

	public void setErreur1(Boolean erreur1) {
		this.erreur1 = erreur1;
	}

	public Boolean getErreur2() {
		return erreur2;
	}

	public void setErreur2(Boolean erreur2) {
		this.erreur2 = erreur2;
	}

	public Boolean getErreur3() {
		return erreur3;
	}

	public void setErreur3(Boolean erreur3) {
		this.erreur3 = erreur3;
	}

	public Boolean getInterrompu1() {
		return interrompu1;
	}

	public void setInterrompu1(Boolean interrompu1) {
		this.interrompu1 = interrompu1;
	}

	public Boolean getInterrompu2() {
		return interrompu2;
	}

	public void setInterrompu2(Boolean interrompu2) {
		this.interrompu2 = interrompu2;
	}

	public Boolean getInterrompu3() {
		return interrompu3;
	}

	public void setInterrompu3(Boolean interrompu3) {
		this.interrompu3 = interrompu3;
	}

	public Boolean getPause1() {
		return pause1;
	}

	public void setPause1(Boolean pause1) {
		this.pause1 = pause1;
	}

	public Boolean getPause2() {
		return pause2;
	}

	public void setPause2(Boolean pause2) {
		this.pause2 = pause2;
	}

	public Boolean getPause3() {
		return pause3;
	}

	public void setPause3(Boolean pause3) {
		this.pause3 = pause3;
	}

	public void setPosition1(Integer position1) {
		this.position1 = position1;
	}

	public void setPosition2(Integer position2) {
		this.position2 = position2;
	}

	public void setPosition3(Integer position3) {
		this.position3 = position3;
	}

	public Integer getPosition1() {
		return position1;
	}

	public Integer getPosition2() {
		return position2;
	}

	public Integer getPosition3() {
		return position3;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	
	

}

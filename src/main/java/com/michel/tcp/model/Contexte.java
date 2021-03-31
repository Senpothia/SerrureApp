package com.michel.tcp.model;

import java.util.ArrayList;
import java.util.List;

import com.michel.tcp.SerrureAppApplication;

public class Contexte {
	
	private Boolean changed;
	private Seance seance = new Seance();
	private Echantillon echantillon1 = new Echantillon();
	private Echantillon echantillon2 = new Echantillon();
	private Echantillon echantillon3 = new Echantillon();
	
	public Contexte() {
		super();
		this.changed = false;
	}

	public Contexte(Boolean changed, Seance seance, Echantillon echantillon1, Echantillon echantillon2,
			Echantillon echantillon3) {
		super();
		this.changed = changed = false;
		this.seance = seance;
		this.echantillon1 = echantillon1;
		this.echantillon2 = echantillon2;
		this.echantillon3 = echantillon3;
	}

	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public Echantillon getEchantillon1() {
		return echantillon1;
	}

	public void setEchantillon1(Echantillon echantillon1) {
		this.echantillon1 = echantillon1;
	}

	public Echantillon getEchantillon2() {
		return echantillon2;
	}

	public void setEchantillon2(Echantillon echantillon2) {
		this.echantillon2 = echantillon2;
	}

	public Echantillon getEchantillon3() {
		return echantillon3;
	}

	public void setEchantillon3(Echantillon echantillon3) {
		this.echantillon3 = echantillon3;
	}
	
	public List<Echantillon> listerEchantillons(){
		
		List<Echantillon> echantillons = new ArrayList<Echantillon>();
		echantillons.add(echantillon1);
		echantillons.add(echantillon2);
		echantillons.add(echantillon3);
		
		return echantillons;
	}
	
	public void factory(Seance seance) {
		
		SerrureAppApplication.contexte.seance = seance;
		List<Echantillon> echs = seance.getEchantillons();
		SerrureAppApplication.contexte.echantillon1 = echs.get(0);
		SerrureAppApplication.contexte.echantillon2 = echs.get(1);
		SerrureAppApplication.contexte.echantillon3 = echs.get(2);
		
	}
	
	
	

}

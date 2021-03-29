package com.michel.tcp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.tcp.constants.Constants;
import com.michel.tcp.model.Echantillon;
import com.michel.tcp.model.Seance;
import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.model.auxiliaire.FormSeance;
import com.michel.tcp.service.jpa.EchantillonService;
import com.michel.tcp.service.jpa.SeanceService;
import com.michel.tcp.service.user.UserConnexion;

@Controller
public class SeanceController {

	@Autowired
	private UserConnexion userConnexion;
	
	@Autowired
	private EchantillonService echantillonService;
	
	@Autowired
	private SeanceService seanceService;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@GetMapping("/creer")
	public String creer(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			model.addAttribute("formSeance", new FormSeance());

			return "creer";

		} else {

			return "redirect:/connexion";
		}

	}
	
	@PostMapping("/creer")
	public String enregistrerSceance(Model model, HttpSession session, FormSeance formSeance) {
		
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			model.addAttribute("formSeance", new FormSeance());
			Seance seance = new Seance();
			seance.setActif(true);
			seance.setEtat(Constants.ARRETE);
			seance.setDate(LocalDateTime.parse(formSeance.getDate()+ " " + "00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			seance.setDescription(formSeance.getDescription());
		
			seanceService.enregistrerSeance(seance);
			Integer id = seance.getId();
			System.out.println("Id scéance: " + id);
			Seance s = seanceService.obtenirSeanceParId(id);
			
			System.out.println(s.toString());
			
			Echantillon echantillon1 = new Echantillon(formSeance, seance, 1);
			Echantillon echantillon2 = new Echantillon(formSeance, seance, 2);
			Echantillon echantillon3 = new Echantillon(formSeance, seance, 3);
			
			
			
			echantillon1.setSeance(s);
			echantillon2.setSeance(s);
			echantillon3.setSeance(s);
			
			System.out.println(echantillon1.toString());
			
			echantillonService.enregistrerEchantillon(echantillon1);
			echantillonService.enregistrerEchantillon(echantillon2);
			echantillonService.enregistrerEchantillon(echantillon3);
			
			
			System.out.println(s.toString());
			List<Echantillon> echantillons = new ArrayList<Echantillon>();
			echantillons.add(echantillon1);
			echantillons.add(echantillon2);
			echantillons.add(echantillon3);
			
			s.setEchantillons(echantillons);
			seanceService.enregistrerSeance(s);
			
			Seance s1 = seanceService.obtenirSeanceParId(id);
			List<Echantillon> echs = s1.getEchantillons();
			System.out.println("Taille liste echs: " + echs.size());
			model.addAttribute("seance", s1);
			int valeur = 0;
			model.addAttribute("valeur", valeur);
		
			return Constants.BOARD;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/suivre")
	public String suivre(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		if (testUser(utilisateur)) {
			
			
			List<Seance> seances = seanceService.obtenirSeanceActive();
			System.out.println("id de la seance active: " + seances.get(0).getId());
			
			model.addAttribute("actif", true);
			List<FormSeance> seancesForm = new ArrayList<FormSeance>();
			for (Seance s: seances) {
				
				FormSeance fs = new FormSeance(s);
				System.out.println("****date: " + fs.getDate());
				seancesForm.add(fs);
			}
			model.addAttribute("seances", seancesForm);
			return "listeSeances";
			
			}else {
				
				return "redirect:/connexion";
				
			}

		
	}

	@GetMapping("/voir")
	public String voir(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
	
		
		List<Seance> seanceInactives = new ArrayList<Seance>();
		seanceInactives = seanceService.obtenirSeancesInanctives();
		System.out.println("Taille liste seance inactives:" + seanceInactives.size());
		
		model.addAttribute("actif", false);
		model.addAttribute("seances", seanceInactives );
		return "listeSeances";
		}else {
			
			return "redirect:/connexion";
			
		}

		
	}
	
	@GetMapping("/sceance/stop/{id}")
	public String stopSeance(@PathVariable(name="id") Integer id,Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
		Seance seance = seanceService.obtenirSeanceParId(id);
		seance.setActif(false);
		seance.setEtat(Constants.ARRETE);
		seanceService.enregistrerSeance(seance);
		
		List<Seance> seanceInactives = new ArrayList<Seance>();
		seanceInactives = seanceService.obtenirSeancesInanctives();
		System.out.println("Taille liste seance inactives:" + seanceInactives.size());
		model.addAttribute("actif", false);
		model.addAttribute("seances", seanceInactives );
		return "listeSeances";
		
		}else {
			
			return "redirect:/connexion";
			
		}
	}
	
	
	@GetMapping("/seance/{id}")
	public String voirSeance(@PathVariable(name="id") Integer id,Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
		Seance seance = seanceService.obtenirSeanceParId(id);
		FormSeance formSeance = new FormSeance(seance);
		model.addAttribute("seance", seance);
		return "seance";
		
		}else {
			
			return "redirect:/connexion";
			
		}
	}
	

	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

}

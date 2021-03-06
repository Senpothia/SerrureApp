package com.michel.tcp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.tcp.service.user.UserConnexion;


import com.michel.tcp.SerrureAppApplication;
import com.michel.tcp.constants.Constants;
import com.michel.tcp.model.Compteurs;
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

	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

	@GetMapping("/creer")
	public String creer(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			List<Seance> seances = seanceService.obtenirSeanceActive();
			System.out.println("taille liste actives: " + seances.size());
			System.out.println("null? :" + seances.isEmpty());
			if (!seances.isEmpty()) {

				return "seanceEnCours";

			} else {

				String token = (String) session.getAttribute("TOKEN");
				token = "Bearer " + token;
				model.addAttribute("formSeance", new FormSeance());
				

				return "creer";
			}

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
			//model.addAttribute("formSeance", new FormSeance());
			Seance seance = new Seance();
			seance.setActif(true);
			seance.setEtat(Constants.ARRET);
			seance.setDate(LocalDateTime.parse(formSeance.getDate() + " " + "00:00:00",
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			seance.setDescription(formSeance.getDescription());

			seanceService.enregistrerSeance(seance);
			Integer id = seance.getId();
			System.out.println("Id sc??ance: " + id);
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
			model.addAttribute("compteurs", new Compteurs());
			SerrureAppApplication.contexte.factory(s);   // Ajouter le 19-04
			SerrureAppApplication.contexte.setChanged(true);
		
			
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
			if (!seances.isEmpty()) {

				System.out.println("id de la seance active: " + seances.get(0).getId());

				model.addAttribute("actif", true);
				List<FormSeance> seancesForm = new ArrayList<FormSeance>();
				for (Seance s : seances) {

					FormSeance fs = new FormSeance(s);
					System.out.println("****date: " + fs.getDate());
					seancesForm.add(fs);
				}
				int id = seancesForm.get(0).getId();
				model.addAttribute("seances", seancesForm);
				model.addAttribute("suivre", true);
				model.addAttribute("id", id);
				return "listeSeances";
			} else {

				return "fin";
			}

		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/board/{id}")
	public String board(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			Seance seanceBase = seanceService.obtenirSeanceParId(id);
			List<Echantillon> echsBase = seanceBase.getEchantillons();
			System.out.println("Taille liste echs: " + echsBase.size());

			model.addAttribute("seance", seanceBase);
			int valeur = 0;
			model.addAttribute("valeur", valeur);

			return Constants.BOARD;
		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/voir")
	public String voir(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			List<Seance> seanceInactives = new ArrayList<Seance>();
			//seanceInactives = seanceService.obtenirSeancesInactives();
			seanceInactives = seanceService.obtenirSeancesOrdonnees();
			System.out.println("Taille liste seance inactives:" + seanceInactives.size());

			model.addAttribute("actif", false);
			model.addAttribute("seances", seanceInactives);
			return "listeSeances";
		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/seance/{id}")
	public String voirSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
			Seance seance = seanceService.obtenirSeanceParId(id);
			List<Echantillon> echs = seance.getEchantillons();
			Collections.sort(echs);
			//FormSeance formSeance = new FormSeance(seance);
			model.addAttribute("seance", seance);
			model.addAttribute("echantillons", echs);
			model.addAttribute("fin", false);
			return "seance";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/board")
	public String board2(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			List<Seance> seances = seanceService.obtenirSeanceActive();
			if (!seances.isEmpty()) {

				Seance seanceBase = seances.get(0);
				List<Echantillon> echsBase = seanceBase.getEchantillons();
				Collections.sort(echsBase);
				System.out.println("Taille liste echs: " + echsBase.size());

				if (!SerrureAppApplication.contexte.getChanged()) {

					System.out.println("Contexte inchang??");
					SerrureAppApplication.contexte.factory(seanceBase);

				} else {

					System.out.println("Contexte chang??");
					Seance seanceProto = SerrureAppApplication.contexte.getSeance();

					seanceBase.setActif(seanceProto.getActif());
					seanceBase.setEtat(seanceProto.getEtat());
					seanceService.enregistrerSeance(seanceBase);

					Echantillon ech1Base = echsBase.get(0);
					ech1Base.setActif(SerrureAppApplication.contexte.getEchantillon1().getActif());
					ech1Base.setErreur(SerrureAppApplication.contexte.getEchantillon1().getErreur());
					ech1Base.setPause(SerrureAppApplication.contexte.getEchantillon1().getPause());
					ech1Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon1().getInterrompu());
					ech1Base.setCompteur(SerrureAppApplication.contexte.getEchantillon1().getCompteur());
					echantillonService.enregistrerEchantillon(ech1Base);

					Echantillon ech2Base = echsBase.get(1);
					ech2Base.setActif(SerrureAppApplication.contexte.getEchantillon2().getActif());
					ech2Base.setErreur(SerrureAppApplication.contexte.getEchantillon2().getErreur());
					ech2Base.setPause(SerrureAppApplication.contexte.getEchantillon2().getPause());
					ech2Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon2().getInterrompu());
					ech2Base.setCompteur(SerrureAppApplication.contexte.getEchantillon2().getCompteur());
					echantillonService.enregistrerEchantillon(ech2Base);

					Echantillon ech3Base = echsBase.get(2);
					ech3Base.setActif(SerrureAppApplication.contexte.getEchantillon3().getActif());
					ech3Base.setErreur(SerrureAppApplication.contexte.getEchantillon3().getErreur());
					ech3Base.setPause(SerrureAppApplication.contexte.getEchantillon3().getPause());
					ech3Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon3().getInterrompu());
					ech3Base.setCompteur(SerrureAppApplication.contexte.getEchantillon3().getCompteur());
					echantillonService.enregistrerEchantillon(ech3Base);

					SerrureAppApplication.contexte.setChanged(false);

				}

				Compteurs compteurs = new Compteurs();
				model.addAttribute("seance", seanceBase);
				int valeur = 0;
				model.addAttribute("valeur", valeur);
				model.addAttribute("compteurs", compteurs);

				return Constants.BOARD;

			} else {

				return "fin";

			}
		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/sceance/stop/{id}")
	public String stopSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
			Seance seance = seanceService.obtenirSeanceParId(id);
			seance.setEtat(Constants.ARRET);
			seance.setActif(false);
			seanceService.enregistrerSeance(seance);

			Seance seanceProto = SerrureAppApplication.contexte.getSeance();

			seanceProto.setEtat(Constants.ARRET);
			seanceProto.setActif(false);

			List<Echantillon> echsBase = seance.getEchantillons();
			Collections.sort(echsBase);
			Echantillon ech1Base = echsBase.get(0);
			ech1Base.setActif(SerrureAppApplication.contexte.getEchantillon1().getActif());
			ech1Base.setErreur(SerrureAppApplication.contexte.getEchantillon1().getErreur());
			ech1Base.setPause(SerrureAppApplication.contexte.getEchantillon1().getPause());
			ech1Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon1().getInterrompu());
			ech1Base.setCompteur(SerrureAppApplication.contexte.getEchantillon1().getCompteur());
			echantillonService.enregistrerEchantillon(ech1Base);

			Echantillon ech2Base = echsBase.get(1);
			ech2Base.setActif(SerrureAppApplication.contexte.getEchantillon2().getActif());
			ech2Base.setErreur(SerrureAppApplication.contexte.getEchantillon2().getErreur());
			ech2Base.setPause(SerrureAppApplication.contexte.getEchantillon2().getPause());
			ech2Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon2().getInterrompu());
			ech2Base.setCompteur(SerrureAppApplication.contexte.getEchantillon2().getCompteur());
			echantillonService.enregistrerEchantillon(ech2Base);

			Echantillon ech3Base = echsBase.get(2);
			ech3Base.setActif(SerrureAppApplication.contexte.getEchantillon3().getActif());
			ech3Base.setErreur(SerrureAppApplication.contexte.getEchantillon3().getErreur());
			ech3Base.setPause(SerrureAppApplication.contexte.getEchantillon3().getPause());
			ech3Base.setInterrompu(SerrureAppApplication.contexte.getEchantillon3().getInterrompu());
			ech3Base.setCompteur(SerrureAppApplication.contexte.getEchantillon3().getCompteur());
			echantillonService.enregistrerEchantillon(ech3Base);

			List<Seance> seanceInactives = new ArrayList<Seance>();
			//seanceInactives = seanceService.obtenirSeancesInactives();
			seanceInactives = seanceService.obtenirSeancesOrdonnees();
			System.out.println("Taille liste seance inactives:" + seanceInactives.size());
			model.addAttribute("actif", false);
			model.addAttribute("seances", seanceInactives);

			String commande = Constants.ARRETER;
			SerrureAppApplication.commande.setMessage(commande);
			SerrureAppApplication.commande.setChanged(true);

			return "listeSeances";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/sceance/start/{id}")
	public String startSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			if (SerrureAppApplication.contexte.getSeance().getEtat().equals("ARRET")) {

				String prefixe = Constants.PREFIXE_GO;
				String commande = sendOrder(prefixe);
				SerrureAppApplication.commande.setMessage(commande);
				SerrureAppApplication.commande.setChanged(true);
				Seance seanceProto = SerrureAppApplication.contexte.getSeance();
				seanceProto.setEtat(Constants.MARCHE);
				SerrureAppApplication.contexte.setChanged(true);

			} else {

				SerrureAppApplication.commande.setMessage(Constants.DEMARRER);
				SerrureAppApplication.commande.setChanged(true);
				Seance seanceProto = SerrureAppApplication.contexte.getSeance();
				seanceProto.setEtat(Constants.MARCHE);
				SerrureAppApplication.contexte.setChanged(true);

			}

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/sceance/pause/{id}")
	public String pauseSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			SerrureAppApplication.commande.setMessage("@SERV:>PAUSE>#");
			SerrureAppApplication.commande.setChanged(true);
			Seance seanceProto = SerrureAppApplication.contexte.getSeance();
			seanceProto.setEtat(Constants.PAUSE);
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/echantillon/pause/{id}/{num}")
	public String pauseNumSeance(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			Echantillon echBase = echantillonService.obtenirEchantillonParId(id);
			echBase.setPause(true);
			echantillonService.enregistrerEchantillon(echBase);

			Seance seanceProto = SerrureAppApplication.contexte.getSeance();
			List<Echantillon> echsProto = seanceProto.getEchantillons();
			Collections.sort(echsProto);
			Echantillon echProto = echsProto.get(num - 1);
			echProto.setPause(true);

			SerrureAppApplication.commande.setMessage("@SERV:>PAUSE>" + num + ">#");
			SerrureAppApplication.commande.setChanged(true);
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/echantillon/stop/{id}/{num}")
	public String stopNumSeance(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			Echantillon echBase = echantillonService.obtenirEchantillonParId(id);
			echBase.setActif(false);
			echBase.setPause(false);
			echBase.setInterrompu(true);
			echantillonService.enregistrerEchantillon(echBase);

			Seance seanceProto = SerrureAppApplication.contexte.getSeance();
			List<Echantillon> echsProto = seanceProto.getEchantillons();
			Collections.sort(echsProto);
			Echantillon echProto = echsProto.get(num - 1);
			echProto.setActif(false);
			echProto.setPause(false);
			echProto.setInterrompu(true);

			Boolean interrompus = SerrureAppApplication.contexte.getEchantillon1().getInterrompu()
					&& SerrureAppApplication.contexte.getEchantillon2().getInterrompu()
					&& SerrureAppApplication.contexte.getEchantillon3().getInterrompu();

			if (!interrompus) {

				SerrureAppApplication.commande.setMessage("@SERV:>STOP>" + num + ">#");
				SerrureAppApplication.commande.setChanged(true);
				SerrureAppApplication.contexte.setChanged(true);
				return "redirect:/board";

			} else {

				Seance SeanceProto = SerrureAppApplication.contexte.getSeance();
				seanceProto.setActif(false);
				seanceProto.setEtat(Constants.ARRET);
				Seance seanceBase = echBase.getSeance();
				seanceBase.setActif(false);
				seanceBase.setEtat(Constants.ARRET);
				seanceService.enregistrerSeance(seanceBase);
				FormSeance formSeance = new FormSeance(seanceBase);
				model.addAttribute("seance", seanceBase);
				model.addAttribute("fin", true);
				SerrureAppApplication.commande.setMessage(Constants.ARRETER);
				SerrureAppApplication.commande.setChanged(true);
				SerrureAppApplication.contexte.setChanged(true);
				return "seance";
			}

		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/echantillon/start/{id}/{num}")
	public String startNumSeance(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			Echantillon echBase = echantillonService.obtenirEchantillonParId(id);
			echBase.setActif(true);
			echBase.setPause(false);
			echantillonService.enregistrerEchantillon(echBase);

			Seance seanceProto = SerrureAppApplication.contexte.getSeance();
			List<Echantillon> echsProto = seanceProto.getEchantillons();
			Collections.sort(echsProto);
			Echantillon echProto = echsProto.get(num - 1);
			echProto.setActif(true);
			echProto.setPause(false);

			SerrureAppApplication.commande.setMessage("@SERV:>START>" + num + ">#");
			SerrureAppApplication.commande.setChanged(true);
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/reset")
	public String reset(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
			
			SerrureAppApplication.contexte.getSeance().setActif(true);
			SerrureAppApplication.contexte.getSeance().setEtat("ARRET");
			List<Echantillon> echantillons = SerrureAppApplication.contexte.getSeance().getEchantillons();
			System.out.println("Taille liste echs (reset): " + echantillons.size());
		
			
			for(int i=0; i<3; i++) {
				
				
				echantillons.get(i).setErreur(false);
				echantillons.get(i).setPause(false);
				echantillons.get(i).setInterrompu(false);
				echantillons.get(i).setActif(true);
				
			}
			
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/espace";

		} else {

			return "redirect:/connexion";

		}

	}

	@PostMapping("/echantillon/set/{id}/{num}")
	public String setCompteurEchantillon(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session, Compteurs compteurs) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		System.out.println("Valeur compteur: " + compteurs.getCompteur());
		if (testUser(utilisateur)) {

			Echantillon echBase = echantillonService.obtenirEchantillonParId(id);
			echBase.setCompteur(compteurs.getCompteur());
			echantillonService.enregistrerEchantillon(echBase);

			Seance seanceProto = SerrureAppApplication.contexte.getSeance();
			List<Echantillon> echsProto = seanceProto.getEchantillons();
			Collections.sort(echsProto);
			Echantillon echProto = echsProto.get(num - 1);
			echProto.setCompteur(compteurs.getCompteur());

			SerrureAppApplication.commande.setMessage("@SERV:>C" + num + ">" + compteurs.getCompteur() + ">#");
			SerrureAppApplication.commande.setChanged(true);
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}

	}

	@PostMapping("/echantillon/set/inhibition")

	public String noSetCompteur(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}
	}

	private String sendOrder(String prefixe) {

		String commande = prefixe;
		String etat; // mode de marche de la s??quence en cours
		Boolean actif = true; // Etat de la s??quence en cours

		Seance seance = SerrureAppApplication.contexte.getSeance();
		etat = seance.getEtat();
		actif = seance.getActif();

		List<Echantillon> echantillons = seance.getEchantillons();
		Collections.sort(echantillons);
		commande = commande + "C1>" + Long.toString(echantillons.get(0).getCompteur()) + ">A1>"
				+ String.valueOf(echantillons.get(0).getActif()) + ">E1>"
				+ String.valueOf(echantillons.get(0).getErreur()) + ">P1>"
				+ String.valueOf(echantillons.get(0).getPause()) + ">I1>"
				+ String.valueOf(echantillons.get(0).getInterrompu()) + ">C2>"
				+ Long.toString(echantillons.get(1).getCompteur()) + ">A2>"
				+ String.valueOf(echantillons.get(1).getActif()) + ">E2>"
				+ String.valueOf(echantillons.get(1).getErreur()) + ">P2>"
				+ String.valueOf(echantillons.get(1).getPause()) + ">I2>"
				+ String.valueOf(echantillons.get(2).getInterrompu()) + ">C3>"
				+ Long.toString(echantillons.get(2).getCompteur()) + ">A3>"
				+ String.valueOf(echantillons.get(2).getActif()) + ">E3>"
				+ String.valueOf(echantillons.get(2).getErreur()) + ">P3>"
				+ String.valueOf(echantillons.get(2).getPause()) + ">I3>"
				+ String.valueOf(echantillons.get(2).getInterrompu()) + ">T1>" + echantillons.get(0).getType() + ">T2>"
				+ echantillons.get(1).getType() + ">T3>" + echantillons.get(2).getType() + ">#";

		System.out.println("Commande sendOrder:" + commande);
		return commande;
	}

	/*
	 * Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
	 * 
	 * if (testUser(utilisateur)) {
	 * 
	 * 
	 * } else {
	 * 
	 * return "redirect:/connexion";
	 * 
	 * }
	 */

}

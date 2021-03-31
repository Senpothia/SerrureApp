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

				return "ok";

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
			model.addAttribute("formSeance", new FormSeance());
			Seance seance = new Seance();
			seance.setActif(true);
			seance.setEtat(Constants.ARRET);
			seance.setDate(LocalDateTime.parse(formSeance.getDate() + " " + "00:00:00",
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
			model.addAttribute("compteurs", new Compteurs());

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

				return "ok";
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

			/*
			 * Echantillon ech1Base = echsBase.get(0); Echantillon e1Proto =
			 * SerrureAppApplication.echantillons.get(0);
			 * ech1Base.setActif(e1Proto.getActif());
			 * ech1Base.setCompteur(e1Proto.getCompteur());
			 * ech1Base.setErreur(e1Proto.getErreur());
			 * ech1Base.setInterrompu(e1Proto.getInterrompu());
			 * ech1Base.setPause(e1Proto.getPause());
			 * 
			 * Echantillon ech2Base = echsBase.get(1); Echantillon e2Proto =
			 * SerrureAppApplication.echantillons.get(1);
			 * ech2Base.setActif(e2Proto.getActif());
			 * ech2Base.setCompteur(e2Proto.getCompteur());
			 * ech2Base.setErreur(e2Proto.getErreur());
			 * ech2Base.setInterrompu(e2Proto.getInterrompu());
			 * ech2Base.setPause(e2Proto.getPause());
			 * 
			 * Echantillon ech3Base = echsBase.get(2); Echantillon e3Proto =
			 * SerrureAppApplication.echantillons.get(2);
			 * ech3Base.setActif(e3Proto.getActif());
			 * ech3Base.setCompteur(e3Proto.getCompteur());
			 * ech3Base.setErreur(e3Proto.getErreur());
			 * ech3Base.setInterrompu(e3Proto.getInterrompu());
			 * ech3Base.setPause(e3Proto.getPause());
			 * 
			 * List<Echantillon> EchsNew = new ArrayList<Echantillon>();
			 * EchsNew.add(ech1Base); EchsNew.add(ech2Base); EchsNew.add(ech3Base);
			 * 
			 * seanceBase.setActif(SerrureAppApplication.seance.getActif());
			 * seanceBase.setEtat(SerrureAppApplication.seance.getEtat());
			 * seanceBase.setEchantillons(EchsNew);
			 */
			model.addAttribute("seance", seanceBase);
			int valeur = 0;
			model.addAttribute("valeur", valeur);

			return Constants.BOARD;
		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/board")
	public String board2(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			List<Seance> seances = seanceService.obtenirSeanceActive();
			Seance seanceBase = seances.get(0);
			List<Echantillon> echsBase = seanceBase.getEchantillons();
			System.out.println("Taille liste echs: " + echsBase.size());

			if (!SerrureAppApplication.contexte.getChanged()) {

				SerrureAppApplication.contexte.factory(seanceBase);

			} else {

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
			FormSeance formSeance = new FormSeance(seance);
			model.addAttribute("seance", seance);
			return "seance";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/sceance/stop/{id}")
	public String stopSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {
			Seance seance = seanceService.obtenirSeanceParId(id);
			seance.setActif(false);
			seance.setEtat(Constants.ARRET);
			seanceService.enregistrerSeance(seance);

			SerrureAppApplication.contexte.setOrdre("@SERV:>STOP>#");
			SerrureAppApplication.contexte.setChanged(true);

			List<Seance> seanceInactives = new ArrayList<Seance>();
			seanceInactives = seanceService.obtenirSeancesInanctives();
			System.out.println("Taille liste seance inactives:" + seanceInactives.size());
			model.addAttribute("actif", false);
			model.addAttribute("seances", seanceInactives);
			return "listeSeances";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/sceance/start/{id}")
	public String arreterSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			SerrureAppApplication.contexte.setOrdre("@SERV:>START>#");
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}
	}

	@GetMapping("/sceance/pause/{id}")
	public String pauseSeance(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			SerrureAppApplication.contexte.setOrdre("@SERV:>PAUSE>#");
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

			SerrureAppApplication.contexte.setOrdre("@SERV:>PAUSE>" + num + ">#");
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

			SerrureAppApplication.contexte.setOrdre("@SERV:>STOP>" + num + ">#");
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}

	}

	@GetMapping("/echantillon/start/{id}/{num}")
	public String startNumSeance(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			SerrureAppApplication.contexte.setOrdre("@SERV:>START>" + num + ">#");
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

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
			
			SerrureAppApplication.contexte.setOrdre("@SERV:>C" + num + ">" + compteurs.getCompteur() + ">#");
			Echantillon ech = SerrureAppApplication.contexte.getSeance().getEchantillons().get(num);
			ech.setCompteur(compteurs.getCompteur());
			SerrureAppApplication.contexte.setChanged(true);

			return "redirect:/board";

		} else {

			return "redirect:/connexion";

		}

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

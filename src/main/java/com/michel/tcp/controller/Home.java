package com.michel.tcp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.tcp.constants.Constants;
import com.michel.tcp.model.Login;
import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.model.auxiliaire.FormCompte;
import com.michel.tcp.model.auxiliaire.UtilisateurAux;
import com.michel.tcp.service.user.UserCompte;
import com.michel.tcp.service.user.UserConnexion;

@Controller
public class Home {

	@Autowired
	private UserConnexion userConnexion;

	@Autowired
	private UserCompte userCompte;

	@GetMapping("/")
	public String accueil(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PAGE_ACCUEIL;
	}

	@GetMapping("/aide")
	public String aide(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.AIDE;
	}

	@GetMapping("/presentation")
	public String presentation(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PRESENTATION;
	}

	@GetMapping("/connexion")
	public String connexion(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.CONNEXION;
	}

	@PostMapping("/connexion") // Traitement formulaire de connexion
	public String demandeConnexion(Login login, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.identifierUtilisateur(login, session);

		if (utilisateur != null) {
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);

			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/connexion?error=true";
		}
	}

	@GetMapping("/compte") // Accès formulaire de création de compte
	public String compte(Model model) {

		FormCompte formCompte = new FormCompte();
		model.addAttribute("formCompte", formCompte);
		return Constants.CREATION_COMPTE;
	}

	@PostMapping("/compte") // Création du compte
	public String creationCompte(Model model, FormCompte formCompte) {

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom(formCompte.getPrenom());
		utilisateur.setNom(formCompte.getNom());
		utilisateur.setPassword(formCompte.getPassword());
		utilisateur.setUsername(formCompte.getUsername());
		utilisateur.setRole("USER");

		userCompte.creerCompte(utilisateur);

		return Constants.CONNEXION;
	}

	@GetMapping("/espace")
	public String espace(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {

			return Constants.CONNEXION;

		} else {

			return Constants.ESPACE_PERSONEL;
		}

	}

	@GetMapping("/compte/modifier")
	public String modifierCompte(@RequestParam(name = "error", required = false) boolean error, Model model,
			HttpSession session) {

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		FormCompte formCompte = new FormCompte();
		formCompte.setNom(utilisateur.getNom());
		formCompte.setPrenom(utilisateur.getPrenom());
		formCompte.setUsername(utilisateur.getUsername());
		model.addAttribute("formCompte", formCompte);
		model.addAttribute("authentification", true);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("error", error);

		return Constants.MODIFIER_COMPTE;
	}

	@PostMapping("/compte/modifier")
	public String enregitrementModification(Model model, HttpSession session, FormCompte formCompte) {

		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");

		UtilisateurAux utilisateurAux = new UtilisateurAux();
		utilisateurAux.setId(utilisateur.getId());
		utilisateurAux.setPrenom(formCompte.getPrenom());
		utilisateurAux.setNom(formCompte.getNom());

		if (!formCompte.getPassword().equals("")) {

			utilisateurAux.setToken(formCompte.getPassword());
			utilisateurAux.setUsername(formCompte.getUsername());
			utilisateurAux.setRole("USER");

			utilisateur.setPrenom(formCompte.getPrenom());
			utilisateur.setNom(formCompte.getNom());

			session.setAttribute("utilisateur", utilisateur);

			userCompte.modifierCompte(utilisateur.getId(), token, utilisateurAux);
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);

			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/compte/modifier?error=true";
		}

	}

	@GetMapping("/login")
	public String logout(HttpSession session) {

		session.invalidate();
		return Constants.PAGE_ACCUEIL;
	}

	// Traitement connexion de l'appli Windows

	@PostMapping("/connexion/apps") // Traitement formulaire de connexion
	public ResponseEntity<String> demandeConnexionApp(@RequestBody Login login, Model model, HttpSession session) {

		System.out.println("Traitement demande connexion apps");
	
		Utilisateur utilisateur = userConnexion.identifierUtilisateur(login, session);

		if (utilisateur != null) {

			System.out.println("nom utilisateur: " + utilisateur.getNom());
			return ResponseEntity.status(HttpStatus.OK).body("Authorised");

		} else {

			return ResponseEntity.status(HttpStatus.OK).body("Access denied");
		}

	}

}

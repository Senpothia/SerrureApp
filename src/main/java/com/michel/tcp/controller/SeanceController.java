package com.michel.tcp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.service.user.UserConnexion;

@Controller
public class SeanceController {

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/creer")
	public String creer(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			return "ok";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/suivre")
	public String suivre() {

		return "suivre";
	}

	@GetMapping("/voir")
	public String voir() {

		return "seances";
	}

	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

}

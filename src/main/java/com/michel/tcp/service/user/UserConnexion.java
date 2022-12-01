package com.michel.tcp.service.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.michel.tcp.model.Login;
import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.model.auxiliaire.UtilisateurAux;

@Service
public class UserConnexion {
	
	@Autowired
	private UserCompte userCompte;
	
	public Utilisateur identifierUtilisateur(Login login, HttpSession session) {
		
		try {
		ResponseEntity<UtilisateurAux> userBody = userCompte.generate(login);
		HttpStatus code = userBody.getStatusCode();
		
		UtilisateurAux userAux = userBody.getBody();
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(userAux.getId());
		utilisateur.setNom(userAux.getNom());
		utilisateur.setRole(userAux.getRole());
		utilisateur.setPrenom(userAux.getPrenom());
		utilisateur.setUsername(userAux.getUsername());
		
		String token = userAux.getToken();
		
		session.setAttribute("USER", utilisateur);
		session.setAttribute("TOKEN", token);
		
		return utilisateur;
		
		} catch (Exception e) {
			
			
			return null;
		}
			
	}
	
	public Utilisateur obtenirUtilisateur (HttpSession session, Model model) {
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		if (utilisateur == null) {
			
			model.addAttribute("authentification", false);
			
		}else {
			
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);
			
		}
		return utilisateur;
		
	}

	public ResponseEntity<UtilisateurAux> identifierApps(Login login, HttpSession session) {
		
		try {
			ResponseEntity<UtilisateurAux> userBody = userCompte.generate(login);
			HttpStatus code = userBody.getStatusCode();
			
			UtilisateurAux userAux = userBody.getBody();
			
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setId(userAux.getId());
			utilisateur.setNom(userAux.getNom());
			utilisateur.setRole(userAux.getRole());
			utilisateur.setPrenom(userAux.getPrenom());
			utilisateur.setUsername(userAux.getUsername());
			
			String token = userAux.getToken();
			
			session.setAttribute("USER", utilisateur);
			session.setAttribute("TOKEN", token);
			
			return userBody;
			
		}catch (Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
	}


}

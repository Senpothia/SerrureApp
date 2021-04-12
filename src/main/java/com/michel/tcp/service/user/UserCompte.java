package com.michel.tcp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.michel.tcp.model.Login;
import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.model.auxiliaire.UtilisateurAux;
import com.michel.tcp.repository.UtilisateurRepo;
import com.michel.tcp.security.JWTGenerator;
import com.michel.tcp.service.jpa.UtilisateurService;

@Service
public class UserCompte {

	@Autowired
	UtilisateurRepo userRepo;

	@Autowired
	PasswordEncoder encoder;


	
	@Autowired
	UtilisateurService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private JWTGenerator jwtGenerator;
	
	public UserCompte(JWTGenerator jwtGenerator) {
		
		this.jwtGenerator = jwtGenerator;
	}

	public void creerCompte(Utilisateur user) {

		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		userRepo.save(user);

	}

	public void modifierCompte(Integer id, String token, UtilisateurAux utilisateurAux) {
		// TODO Auto-generated method stub

	}

	public ResponseEntity<UtilisateurAux> generate(Login login) {
		System.out.println("**Entrée POST service");
		Utilisateur jwtUser = new Utilisateur();
		jwtUser = existUtilisateur(login);

		if (jwtUser != null) {

			UtilisateurAux userAux = new UtilisateurAux();
			userAux.setId(jwtUser.getId());
			userAux.setNom(jwtUser.getNom());
			userAux.setPrenom(jwtUser.getPrenom());
			userAux.setRole(jwtUser.getRole());
			userAux.setUsername(jwtUser.getUsername());
			String token = jwtGenerator.generate(jwtUser);
			userAux.setToken(token);
			return new ResponseEntity<UtilisateurAux>(userAux, HttpStatus.OK);

		} else {

			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

	}

	private Utilisateur existUtilisateur(Login login) {

		System.out.println("Login user: " + login.getUser());
		System.out.println("Password user: " + login.getPassword());
		System.out.println("Login user: " + passwordEncoder.encode(login.getPassword()));

		try {

			Utilisateur utilisateur = userService.obtenirUserParlogin(login.getUser(), login.getPassword());
			//utilisateur.setRole("Admin");
			System.out.println("Connexion réussion: " + utilisateur.getPrenom() + " " + utilisateur.getNom());
			return utilisateur;

		} catch (Exception e) {

			System.out.println("Utilisateur non identifié");
			return null;
		}

	}

}

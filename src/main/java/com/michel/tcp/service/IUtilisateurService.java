package com.michel.tcp.service;

import java.util.List;

import com.michel.tcp.model.Utilisateur;

public interface IUtilisateurService {

	void ajouterUser(Utilisateur user);

	List<Utilisateur> listerUsers();

	Utilisateur obtenirUser(Integer id);

	Utilisateur obtenirUser(String string);

	Utilisateur obtenirUserParEmail(String email);

	void modifierUser(Utilisateur user);

	void supprimerUser(Utilisateur user);

}

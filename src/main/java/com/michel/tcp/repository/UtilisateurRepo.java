package com.michel.tcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.model.auxiliaire.UtilisateurAux;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {

	Utilisateur findByUsername(String username);

	void save(UtilisateurAux user);

	

}

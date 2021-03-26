package com.michel.tcp.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.michel.tcp.model.Utilisateur;
import com.michel.tcp.repository.UtilisateurRepo;
import com.michel.tcp.service.IUtilisateurService;

@Service
public class UtilisateurService implements IUtilisateurService{
	
	@Autowired
	UtilisateurRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public List<Utilisateur> listerUsers() {

		List<Utilisateur> users = userRepo.findAll();
		return users;
	}

	@Override
	public Utilisateur obtenirUser(Integer id) {

		Utilisateur user = userRepo.getOne(id);
		return user;
	}

	@Override
	public Utilisateur obtenirUser(String string) {


		// User user = userRepo.findByIdentity(string);

		return null;
	}

	@Override
	public Utilisateur obtenirUserParEmail(String email) {

		Utilisateur user = userRepo.findByUsername(email);
		return user;
	}
	
	

	@Override
	public void ajouterUser(Utilisateur user) {

		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		userRepo.save(user);

	}

	@Override
	public void modifierUser(Utilisateur user) {
		userRepo.save(user);

	}

	@Override
	public void supprimerUser(Utilisateur user) {
		userRepo.delete(user);

	}

	public Utilisateur obtenirUserParlogin(String email, String password) {
		
		Utilisateur utilisateur = userRepo.findByUsername(email);
		if (encoder.matches(password, utilisateur.getPassword())){
			
			return utilisateur;

		} else

			return null;

	}

}

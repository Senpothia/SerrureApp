package com.michel.tcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.michel.tcp.model.Utilisateur;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {

}

package com.michel.tcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michel.tcp.model.Echantillon;



public interface EchantillonRepo extends JpaRepository<Echantillon, Integer>{

}

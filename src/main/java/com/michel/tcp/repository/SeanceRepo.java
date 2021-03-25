package com.michel.tcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michel.tcp.model.Seance;


public interface SeanceRepo extends JpaRepository<Seance, Integer>{

}

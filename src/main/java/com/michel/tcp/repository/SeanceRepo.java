package com.michel.tcp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michel.tcp.model.Seance;


public interface SeanceRepo extends JpaRepository<Seance, Integer>{

	List<Seance> findByActif(boolean b);

	List<Seance> findByActifOrderByIdDesc(boolean b);

}

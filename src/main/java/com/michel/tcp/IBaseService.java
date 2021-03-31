package com.michel.tcp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michel.tcp.model.Echantillon;
import com.michel.tcp.model.Seance;

public interface IBaseService extends JpaRepository<Seance, Integer> {

}

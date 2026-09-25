package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
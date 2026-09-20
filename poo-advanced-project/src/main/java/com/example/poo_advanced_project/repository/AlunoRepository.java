package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<Aluno> findByEmailIgnoreCase(String email);

    List<Aluno> findByAssinaturaAtivaTrue();
}

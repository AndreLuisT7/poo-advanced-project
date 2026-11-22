package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findBySiglaIgnoreCase(String sigla);
}

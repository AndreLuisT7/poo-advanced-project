package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByDataHoraBetweenOrderByDataHoraAsc(LocalDateTime inicio, LocalDateTime fim);

    List<Evento> findByDataHoraGreaterThanEqualOrderByDataHoraAsc(LocalDateTime inicio);

    List<Evento> findByInstituicaoIdAndDataHoraBetweenOrderByDataHoraAsc(
            Long instituicaoId, LocalDateTime inicio, LocalDateTime fim);
}

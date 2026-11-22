package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("""
            select e from Evento e join fetch e.instituicao
            where e.dataHora >= :inicio
            order by e.dataHora asc
            """)
    List<Evento> buscarProximos(LocalDateTime inicio);

    @Query("""
            select e from Evento e join fetch e.instituicao
            order by e.dataHora asc
            """)
    List<Evento> buscarTodosComInstituicao();

    @Query("""
            select e from Evento e join fetch e.instituicao i
            where i.id = :instituicaoId and e.dataHora between :inicio and :fim
            order by e.dataHora asc
            """)
    List<Evento> buscarDaSemanaPorInstituicao(Long instituicaoId, LocalDateTime inicio, LocalDateTime fim);
}

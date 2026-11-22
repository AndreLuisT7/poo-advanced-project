package com.example.poo_advanced_project.repository;

import com.example.poo_advanced_project.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMatriculaIgnoreCaseAndInstituicaoId(String matricula, Long instituicaoId);

    Optional<Aluno> findByEmailIgnoreCase(String email);

    /**
     * Traz a instituicao na mesma consulta. Sem o join fetch, o envio agendado
     * quebraria com LazyInitializationException: o @Scheduled roda fora de
     * transacao e nao teria sessao aberta para carregar a instituicao depois.
     */
    @Query("select a from Aluno a join fetch a.instituicao where a.assinaturaAtiva = true")
    List<Aluno> buscarInscritosComInstituicao();
}
